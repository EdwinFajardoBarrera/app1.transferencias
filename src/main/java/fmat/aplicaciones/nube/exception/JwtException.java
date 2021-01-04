package fmat.aplicaciones.nube.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class JwtException extends RuntimeException {

    public JwtException() {
        super("Ocurrio un error al validar el token");
    }

    public JwtException(String mensaje) {
        super(mensaje);
    }
    
}
