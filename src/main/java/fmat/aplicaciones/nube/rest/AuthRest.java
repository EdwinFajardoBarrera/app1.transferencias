package fmat.aplicaciones.nube.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.model.request.UsuarioRequest;
import fmat.aplicaciones.nube.model.request.LoginRequest;
import fmat.aplicaciones.nube.service.AuthService;
import fmat.aplicaciones.nube.service.UsuarioService;
import fmat.aplicaciones.nube.model.LoginObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api")
public class AuthRest {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> postRegister(@RequestBody @Valid UsuarioRequest signupRequest)
            throws URISyntaxException {
        Usuario usuario = authService.register(signupRequest);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginObject> getLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/quienSoy")
    public ResponseEntity<Usuario> getQuienSoy() {
        Usuario usuario = (Usuario)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(usuario);
    }
    
}
