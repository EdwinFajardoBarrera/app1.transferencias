package fmat.aplicaciones.nube.model;
import javax.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "clave",unique = true)
    private String clave;

    @OneToOne
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    private Cuenta cuenta;

    public Usuario(){
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta idCuenta) {
        this.cuenta = idCuenta;
    }

    @Override
    public String toString() {
        return "{" +
                " usuario='" + getNombre() + "'" +
                " clave='" + getClave() + "'" +
                " idCuenta='" + getCuenta() +
                "}";
    }
}