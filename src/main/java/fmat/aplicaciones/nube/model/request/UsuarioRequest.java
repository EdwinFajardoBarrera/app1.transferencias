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

    @NotNull(message = "Favor de ingresar su email")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotNull(message = "Favor de ingresar su clave")
    @Size(min = 9, max = 50)
    @NotEmpty
    private String password;

    public UsuarioRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}