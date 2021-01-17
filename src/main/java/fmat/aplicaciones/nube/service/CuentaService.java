package fmat.aplicaciones.nube.service;

import fmat.aplicaciones.nube.exception.ExistObjectException;
import fmat.aplicaciones.nube.exception.NotFoundException;
import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.model.request.CuentaRequest;
import fmat.aplicaciones.nube.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> getCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuenta(Integer id) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            return cuenta.get();
        }
        throw new NotFoundException("La cuenta no se encuentra");
    }

    public Cuenta createCuenta(@Valid @RequestBody CuentaRequest request) {
        if (cuentaRepository.findByNoCuenta(request.getNoCuenta()) == null) {
            Cuenta cuenta = new Cuenta();
            cuenta.setNoCuenta(request.getNoCuenta());
            cuenta.setBalance(request.getBalance());
            cuentaRepository.save(cuenta);
            return cuenta;
        }
        throw new ExistObjectException("El número de cuenta que desea guardar ya existe registrado ");
    }

    public Cuenta updateCuenta(Integer id, CuentaRequest request) {
        Cuenta cuenta = getCuenta(id);
        cuenta.setNoCuenta(request.getNoCuenta());
        cuenta.setBalance(request.getBalance());
        cuentaRepository.save(cuenta);
        return cuenta;
    }

    public Cuenta deleteCuenta(Integer id) {
        Cuenta cuenta = getCuenta(id);
        cuentaRepository.deleteById(id);
        return cuenta;
    }
}
