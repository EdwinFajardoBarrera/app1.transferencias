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
        user.setClave(request.getClave());
        if (request.getCuenta()!=null){
            Cuenta cuenta = getCuenta(request.getCuenta());
            user.setCuenta(cuenta);
        }
        usuarioRepository.save(user);
        return user;
    }

    /*
     * @Transactional public Usuario crear(UsuarioRequest request) { Usuario
     * usuarioCrear = new Usuario();
     *
     * usuarioCrear.setUsuario(request.getUsuario());
     * usuarioCrear.setPassword(request.getPassword());
     *
     * String token = UUID.randomUUID().toString(); usuarioCrear.setToken(token);
     *
     * Usuario usuarioGuardado = usuarioRepository.save(usuarioCrear);
     *
     * Alumno alumno = new Alumno();
     *
     * alumno.setNombre(request.getNombre()); alumno.setUsuario(usuarioGuardado); //
     * Relacionar 2 entidades
     *
     * alumno = alumnoRepository.save(alumno);
     *
     * return usuarioGuardado; }
     */

    /**
    @Transactional
    public Usuario updateUsuario(Integer id, UsuarioRequest request) {
        Usuario user = findUser(id);

        user.setUsuario(request.getUsuario());
        user.setPassword(request.getPassword());
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user = usuarioRepository.save(user);

        return user;

    }

    public Usuario getUsuario(Integer id) {

        return findUser(id);
    }

    public Usuario deleteUsuario(Integer id) {
        Usuario user = findUser(id);
        usuarioRepository.deleteById(id);

        return user;
    }

    public Usuario findUser(Integer id) {
        Optional<Usuario> opt = usuarioRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new NotFoundException("No se encontro el usuario");
    }

    public Usuario deleteToken(Integer id){
        Usuario user = findUser(id);

        user.setToken("");
        user = usuarioRepository.save(user);

        return user;
    }

    public Usuario register(RegisterRequest request){
        Usuario usuario= usuarioRepository.findByUsuario(request.getUsuario());
        Boolean usuarioExist = usuario != null;
        if(usuarioExist){
            throw new InvalidOperationException("El usuario ya esta registrado en el sistema");
        }
        Usuario usuarioCrear = new Usuario();
        usuarioCrear.setPassword(request.getPassword());
        usuarioCrear.setUsuario(request.getUsuario());
        Usuario newUser = usuarioRepository.save(usuarioCrear);

        return newUser;
    }**/

}