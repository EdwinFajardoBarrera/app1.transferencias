package fmat.aplicaciones.nube.rest;

import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CuentaRest {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/cuentas")
    public ResponseEntity<List<Cuenta>> getCuentas() {
        List<Cuenta> cuentas = cuentaService.getCuentas();
        return ResponseEntity.status(HttpStatus.OK).body(cuentas);
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Cuenta> getCuenta(@PathVariable Integer id) {
        Cuenta cuenta = cuentaService.getCuenta(id);
        return  ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }


}
