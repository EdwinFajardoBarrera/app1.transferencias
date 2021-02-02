package fmat.aplicaciones.nube.dto;

import com.opencsv.bean.CsvBindByName;

public class PagoCsvDTO {

    @CsvBindByName
    private Double monto;

    @CsvBindByName
    private String cuentaDestino;

    public PagoCsvDTO(Double monto, String cuentaDestino) {
        this.monto = monto;
        this.cuentaDestino = cuentaDestino;
    }
    public PagoCsvDTO(){}
    
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
}
