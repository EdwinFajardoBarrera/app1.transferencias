package fmat.aplicaciones.nube.rest;

import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.model.request.UsuarioRequest;
import fmat.aplicaciones.nube.service.RabbitService;
import fmat.aplicaciones.nube.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable Integer id) {
        Usuario user = usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
        Usuario user = usuarioService.updateUsuario(id, request);
        return ResponseEntity.ok().body(user);
    }

}