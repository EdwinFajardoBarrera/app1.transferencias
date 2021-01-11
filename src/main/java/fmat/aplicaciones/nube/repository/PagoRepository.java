package fmat.aplicaciones.nube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import fmat.aplicaciones.nube.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>{
    
}
