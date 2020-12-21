package fmat.aplicaciones.nube.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CuentaRequest {
    @NotNull(message = "Favor de ingresar su número de cuenta")
    @Size(min = 18, max = 18)
    @NotEmpty
    private String noCuenta;

    @NotNull(message = "Favor de ingresar su balance")
    @NotEmpty
    private Double balance;

    public CuentaRequest() {}

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
