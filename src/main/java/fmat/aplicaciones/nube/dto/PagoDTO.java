package fmat.aplicaciones.nube.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.stereotype.Component;

public class PagoDTO {

    private Integer idPago;
    private BigDecimal monto;
    private String cuentaOrigen;
    private String cuentaDestino;
    private String fechaRegistro;
    @JsonIgnore
    private String fechaProcesa;
    private String estado;
    @JsonIgnore
    private Integer retry;

    public PagoDTO(Integer idPago, BigDecimal monto, String cuentaOrigen, String cuentaDestino, String fechaRegistro,
            String fechaProcesa, String estado) {
        this.idPago = idPago;
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.fechaRegistro = fechaRegistro;
        this.fechaProcesa = fechaProcesa;
        this.estado = estado;
        this.retry = 0;
    }

    public PagoDTO() {
        this.retry = 0;
    }

    public Integer getIdPago() {
        return this.idPago;
    }

    public BigDecimal getMonto() {
        return this.monto;
    }

    public String getCuentaOrigen() {
        return this.cuentaOrigen;
    }

    public String getCuentaDestino() {
        return this.cuentaDestino;
    }

    public String getFechaRegistro() {
        return this.fechaRegistro;
    }

    public String getFechaProcesa() {
        return this.fechaProcesa;
    }

    public String getEstado() {
        return this.estado;
    }

    public Integer getRetry() {
        return this.retry;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setFechaProcesa(String fechaProcesa) {
        this.fechaProcesa = fechaProcesa;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    @Override
    public String toString() {
        return "{\"idPago\": " + this.getIdPago() + "," + "\"monto\":" + this.getMonto() + "," + "\"cuentaOrigen\": \""
                + this.getCuentaOrigen() + "\"," + "\"cuentaDestino\": \"" + this.getCuentaDestino() + "\","
                + "\"fechaRegistro\": \"" + this.getFechaRegistro() + "\"," + "\"fechaProcesa\": \""
                + this.getFechaProcesa() + "\"," + "\"estado\": \"" + this.getEstado() + "\"retry\": \""
                + this.getRetry() + "\"}";
    }

}

// private Integer idPago;
// private Double monto;
// private String cuentaOrigen;
// private String cuentaDestino;
// private String fechaRegistro;
// private String fechaProcesa;
// private String estado;