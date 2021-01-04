package fmat.aplicaciones.nube.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("El usuario o la contraseña ingresados son incorrectos.");
    }

    public BadCredentialsException(String mensaje) {
        super(mensaje);
    }
    
}
