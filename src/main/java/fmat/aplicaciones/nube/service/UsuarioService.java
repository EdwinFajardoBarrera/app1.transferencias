package fmat.aplicaciones.nube.service;
import java.util.List;
import java.util.Optional;

import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.exception.NotFoundException;
import fmat.aplicaciones.nube.model.request.UsuarioRequest;
import fmat.aplicaciones.nube.repository.CuentaRepository;
import fmat.aplicaciones.nube.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario deleteUsuario(Integer id){
        Usuario user = getUsuario(id);
        usuarioRepository.deleteById(id);
        return user;
    }

    public Usuario getUsuario(Integer id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new NotFoundException("El usuario no se encuentra");
    }

    public Cuenta getCuenta(Integer id){
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            return cuenta.get();
        }
        throw new NotFoundException("La cuenta respectiva al usuario no se encuentra");
    }

    public Usuario updateUsuario(Integer id, UsuarioRequest request) {
        Usuario user = getUsuario(id);
        user.setNombre(request.getNombre());
        user.setPassword(request.getPassword());
        usuarioRepository.save(user);
        return user;
    }


}