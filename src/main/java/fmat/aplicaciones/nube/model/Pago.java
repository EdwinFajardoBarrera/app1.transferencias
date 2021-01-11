package fmat.aplicaciones.nube.model;

import java.sql.Date;
// import javax.persistence.Column;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;

// import org.springframework.data.annotation.Id;

public class Pago {

  public Pago() {
  }

  // @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // @Column(name = "monto")
  private Double monto;

  // @Column(name = "fecha_procesa")
  private Date fechaProcesa;

  // @Column(name = "fecha_registro")
  private Date fechaRegistro;

  // @Column(name = "estado")
  private String estado;

  // @Column(name = "cuenta_origen")
  private Integer cuentaOrigen;

  // @Column(name = "cuenta_destino")
  private Integer cuentaDestino;

  public Integer getId() {
    return id;
  }

  public Double getMonto() {
    return monto;
  }

  public String getEstado() {
    return estado;
  }

  public Date getFechaProcesa() {
    return fechaProcesa;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public Integer getCuentaDestino() {
    return cuentaDestino;
  }

  public Integer getCuentaOrigen() {
    return cuentaOrigen;
  }

  public void setCuentaDestino(Integer cuentaDestino) {
    this.cuentaDestino = cuentaDestino;
  }

  public void setCuentaOrigen(Integer cuentaOrigen) {
    this.cuentaOrigen = cuentaOrigen;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setFechaProcesa(Date fechaProcesa) {
    this.fechaProcesa = fechaProcesa;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setMonto(Double monto) {
    this.monto = monto;
  }

  @Override
  public String toString() {
    return "{" + "\"id\" : " + this.getId() + ", " + "\"monto\" : " + this.getMonto() + ", " + "\"estado\" : \""
        + this.getEstado() + "\", " + "\"cuenta_origen\" : " + this.getCuentaOrigen() + ", " + "\"cuenta_destino\" : "
        + this.getCuentaDestino() + ", " + "\"fecha_procesa\" : \"" + this.getFechaProcesa() + "\", "
        + "\"fecha_registro\" : \"" + this.getFechaRegistro() + "\"" + "}";
  }

}
