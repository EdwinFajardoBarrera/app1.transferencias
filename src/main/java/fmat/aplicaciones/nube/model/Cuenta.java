package fmat.aplicaciones.nube.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "Cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( name = "no_cuenta")
    private String noCuenta;

    @Column(name = "balance")
    private double banlance;

    public Cuenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public double getBanlance() {
        return banlance;
    }

    public void setBanlance(double banlance) {
        this.banlance = banlance;
    }

    @Override
    public String toString() {
        return "{" +
                " número de cuenta ='" + getNoCuenta() + "'" +
                " balance ='" + getBanlance() + "'" +
                " idCuenta='" + getId() +
                "}";
    }
}
