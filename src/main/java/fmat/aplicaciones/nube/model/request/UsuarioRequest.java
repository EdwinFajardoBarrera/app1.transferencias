package fmat.aplicaciones.nube.model.request;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotNull(message = "Favor de ingresar su nombre")
    @Size(min = 2, max = 50)
    @NotEmpty
    private String nombre;


    @NotNull(message = "Favor de ingresar su clave")
    @Size(min = 9, max = 50)
    @NotEmpty
    private String clave;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}