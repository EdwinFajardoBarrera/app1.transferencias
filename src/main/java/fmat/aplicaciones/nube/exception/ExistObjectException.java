package fmat.aplicaciones.nube.exception;

public class ExistObjectException extends RuntimeException {

    public ExistObjectException() {
        super("La entidad ya se encuentra registrada.");
    }

    public ExistObjectException(String mensaje) {
        super(mensaje);
    }

}