package fmat.aplicaciones.nube.rest;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    /**@GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuario() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }**/

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable Integer id) {
        Usuario user = usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().body(user);
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