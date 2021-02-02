package fmat.aplicaciones.nube.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureException;
import fmat.aplicaciones.nube.exception.JwtException;
import fmat.aplicaciones.nube.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.repository.UsuarioRepository;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;
    DecodedToken token = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        token = DecodedToken.getDecoded(jwtToken);
        username = token.sub;
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (Exception e) {
        // e.printStackTrace();
        sendErrorMessage(401, "Token invalido", response);
        // System.out.println("JWT Token has expired");
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        Usuario usuario = this.usuarioRepository.findByEmail(username);
        String secret = usuario.getSecret();

        try {
          Claims claims = jwtTokenUtil.validateTokenSecret(jwtToken, secret);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
          // System.out.println("Error: " + e.getMessage() + "\n");
          // System.out.println("Excepcion " + e);
          sendErrorMessage(401, "Error en el formato del token", response);
          return;

        } catch (SignatureException ex) {
          // System.out.println("Error: " + ex.getMessage() + "\n");
          // System.out.println("Excepcion " + ex);
          sendErrorMessage(401, "Token invalido", response);
          return;
        } catch(ExpiredJwtException ec){
          System.out.println("Error: " + ec.getMessage() + "\n");
          System.out.println("Excepcion " + ec);
          sendErrorMessage(401, "El token expiró, favor de ingresar de nuevo al sistema", response);
        }

        if (jwtTokenUtil.validateToken(token, usuario)) {
          Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
          sendErrorMessage(401, "Token invalido", response);
        }
      }
    }

    chain.doFilter(request, response);
  }

  private void sendErrorMessage(int code, String message, HttpServletResponse response)
      throws ServletException, IOException {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(code);
    errorResponse.setMessage(message);

    byte[] responseToSend = restResponseBytes(errorResponse);
    ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
    ((HttpServletResponse) response).setStatus(code);
    response.getOutputStream().write(responseToSend);
  }

  private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
    return serialized.getBytes();
  }
}
