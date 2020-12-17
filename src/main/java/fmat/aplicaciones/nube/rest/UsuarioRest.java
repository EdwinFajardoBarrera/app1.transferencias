package fmat.aplicaciones.nube.rest;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fmat.aplicaciones.nube.model.request.UsuarioRequest;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuario() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable Integer id) {
        Usuario user = usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().body(user);
    }
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> postRegister(@RequestBody @Valid UsuarioRequest signupRequest)
            throws URISyntaxException {
        Usuario usuario = usuarioService.register(signupRequest);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

 /**
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario u = usuarioService.getNombre(id);
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }


    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
        Usuario user = usuarioService.updateUsuario(id,request);

        return ResponseEntity.ok()
                .body(user);
    }


**/
}