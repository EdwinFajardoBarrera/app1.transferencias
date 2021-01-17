package fmat.aplicaciones.nube.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
  // public Cuenta findByNoCuenta(String noCuenta);

  public List<Pago> findByCuentaOrigen(Cuenta cuentaOrigen);
}
