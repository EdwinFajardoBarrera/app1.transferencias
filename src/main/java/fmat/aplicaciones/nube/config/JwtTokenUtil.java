package fmat.aplicaciones.nube.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import fmat.aplicaciones.nube.model.Usuario;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;

  public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

  // retrieve username from jwt token
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  // retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  // for retrieveing any information from token we will need the secret key
  private Claims getAllClaimsFromToken(String token) {
    // String secret = algo;
    return Jwts.parser().parseClaimsJwt(token).getBody();
  }

  // check if the token has expired
  private Boolean isTokenExpired(DecodedToken token) {
    try {
      Date date = new Date(Long.parseLong(token.ext));
      return date.before(new Date());
    } catch (Exception e) {
      e.printStackTrace();
      return true;
    }
  }

  // generate token for user
  public String generateToken(Usuario usuario) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("ext", ZonedDateTime.now().toInstant().toEpochMilli() + JWT_TOKEN_VALIDITY);
    return doGenerateToken(claims, usuario.getEmail(), usuario.getSecret());
  }

  // while creating the token -
  // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
  // 2. Sign the JWT using the HS512 algorithm and secret key.
  // 3. According to JWS Compact
  // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  // compaction of the JWT to a URL-safe string
  private String doGenerateToken(Map<String, Object> claims, String subject, String secret) {
    System.out.println(new Date(ZonedDateTime.now().toInstant().toEpochMilli()).toString());
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(ZonedDateTime.now().toInstant().toEpochMilli()))
        .setExpiration(new Date(ZonedDateTime.now().toInstant().toEpochMilli() + JWT_TOKEN_VALIDITY))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  // validate token
  public Boolean validateToken(DecodedToken token, Usuario usuario) {
    final String username = usuario.getEmail();
    return (username.equals(usuario.getEmail()) && !isTokenExpired(token));
  }

  public Claims validateTokenSecret(String token, String secret) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

}