package fmat.aplicaciones.nube.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericErrorException extends RuntimeException {

    public GenericErrorException() {
        super("Ocurrio un error al realizar su operación");
    }

    public GenericErrorException(String mensaje) {
        super(mensaje);
    }
    
}