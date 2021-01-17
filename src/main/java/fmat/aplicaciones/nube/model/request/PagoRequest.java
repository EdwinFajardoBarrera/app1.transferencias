package fmat.aplicaciones.nube.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.*;

public class PagoRequest {

    @NotNull(message = "Favor de ingresar la cuenta a transferir el dinero")
    @Size(min = 12)
    @NotEmpty
    private String cuentaDestino;

    @NotNull(message = "Favor de ingresar el monto a transferir")
    @Min(50)
    @Max(10000)
    private Double monto;

    public PagoRequest(String cuentaDestino, double monto) {
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
    }

    public Double getMonto() {
        return this.monto;
    }

    public String getCuentaDestino() {
        return this.cuentaDestino;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public String toString() {
        return "{" + " cuentaDestino ='" + getCuentaDestino() + "'" + " monto ='" + getMonto() + "}";
    }

}
