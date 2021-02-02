package fmat.aplicaciones.nube.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CuentaRequest {
    @NotNull(message = "Favor de ingresar su número de cuenta")
    @Size(min = 9, max = 9)
    @NotEmpty
    private String noCuenta;

    @NotNull(message = "Favor de ingresar su balance")
    private BigDecimal balance;

    public CuentaRequest() {
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
