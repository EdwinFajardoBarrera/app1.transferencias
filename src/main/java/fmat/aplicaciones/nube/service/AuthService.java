package fmat.aplicaciones.nube.service;


import java.util.UUID;

import fmat.aplicaciones.nube.model.request.UsuarioRequest;
import fmat.aplicaciones.nube.model.request.LoginRequest;

import fmat.aplicaciones.nube.exception.InvalidOperationException;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.repository.CuentaRepository;
import fmat.aplicaciones.nube.repository.UsuarioRepository;
import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.LoginObject;
import fmat.aplicaciones.nube.config.JwtTokenUtil;
import fmat.aplicaciones.nube.exception.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public Usuario register(UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());
        Boolean usuarioExist = usuario != null;
        if (usuarioExist) {
            throw new InvalidOperationException("El usuario ya esta registrado en el sistema");
        }
        Usuario usuarioCrear = new Usuario();
        String secret = UUID.randomUUID().toString();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String contra = request.getPassword();
        String hashedPassword = passwordEncoder.encode(contra);
        usuarioCrear.setNombre(request.getNombre());
        usuarioCrear.setPassword(hashedPassword);
        usuarioCrear.setEmail(request.getEmail());
        usuarioCrear.setSecret(secret);

        String noBegin = "MX";
        Cuenta cuenta = new Cuenta();
        String uuid = RandomStringUtils.randomAlphanumeric(10);
        String noCuenta = noBegin + uuid;
        cuenta.setNoCuenta(noCuenta);
        cuenta.setBalance(0);

        cuentaRepository.save(cuenta);

        Cuenta temp = cuentaRepository.findByNoCuenta(noCuenta);

        usuarioCrear.setCuenta(temp);

        Usuario newUser = usuarioRepository.save(usuarioCrear);

        return newUser;
    }

    public LoginObject login(LoginRequest request) {
        Usuario usr = usuarioRepository.findByEmail(request.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String reqUser = request.getEmail();
        String reqPass = request.getPassword();

        boolean isPasswordMatch = passwordEncoder.matches(reqPass, usr.getPassword());

        if (isPasswordMatch && reqUser.equals(usr.getEmail())) {
            JwtTokenUtil tokenUtil = new JwtTokenUtil();

            String token = tokenUtil.generateToken(usr);

            LoginObject lg = new LoginObject();
            lg.setToken(token);
            lg.setUsuario(usr);

            return lg;
        } else {
            throw new BadCredentialsException();
        }
    }
    
}
