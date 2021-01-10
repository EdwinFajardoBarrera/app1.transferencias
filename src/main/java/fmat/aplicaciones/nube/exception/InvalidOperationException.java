package fmat.aplicaciones.nube.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidOperationException extends RuntimeException {

    public InvalidOperationException() {
        super("La operacion es invalida");
    }

    public InvalidOperationException(String mensaje) {
        super(mensaje);
    }
    
}