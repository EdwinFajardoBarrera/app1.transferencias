package fmat.aplicaciones.nube.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "password")
    @JsonIgnore
    private String password;

    public Usuario(Integer id, String nombre,String password,String clave) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.clave= clave;
    }
    public Usuario( String nombre,String clave) {
        this.nombre = nombre;
        this.clave= clave;
    }

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

      /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }



    @Override
    public String toString() {
        return "{" +
                " usuario='" + getNombre() + "'" +
                " clave='" + getClave() + "'" +
                " idCuenta='" +
                "}";
    }
}