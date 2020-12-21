package fmat.aplicaciones.nube.service;

import fmat.aplicaciones.nube.exception.NotFoundException;
import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> getCuentas(){
        return cuentaRepository.findAll();
    }

    public Cuenta getCuenta(Integer id){
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            return cuenta.get();
        }
        throw new NotFoundException("La cuenta no se encuentra");
    }
}
