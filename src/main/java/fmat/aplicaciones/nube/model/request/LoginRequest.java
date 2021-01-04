package fmat.aplicaciones.nube.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequest {

    @Email(message = "El email debe ser valido")
    private String email;

    @NotNull(message = "Favor de ingresar su clave")
    @Size(min = 9, max = 50)
    @NotEmpty
    private String password;

    public LoginRequest(){}

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String clave) {
        this.password = clave;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
    
}
