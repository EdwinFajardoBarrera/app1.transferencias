package fmat.aplicaciones.nube.dto;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;

public class PagoCsvDTO {

    @CsvBindByName
    private BigDecimal monto;

    @CsvBindByName
    private String cuentaDestino;

    public PagoCsvDTO(BigDecimal monto, String cuentaDestino) {
        this.monto = monto;
        this.cuentaDestino = cuentaDestino;
    }
    public PagoCsvDTO(){}
    
    public BigDecimal getMonto() {
        return this.monto;
    }

    public String getCuentaDestino() {
        return this.cuentaDestino;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
}
