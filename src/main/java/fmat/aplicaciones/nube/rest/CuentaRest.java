package fmat.aplicaciones.nube.rest;

import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.request.BalanceRequest;
import fmat.aplicaciones.nube.model.request.CuentaRequest;
import fmat.aplicaciones.nube.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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

    @GetMapping("/cuenta/{id}")
    public ResponseEntity<Cuenta> getCuenta(@PathVariable Integer id) {
        Cuenta cuenta = cuentaService.getCuenta(id);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @PostMapping("/cuenta")
    public ResponseEntity<Cuenta> postCuenta(@Valid @RequestBody CuentaRequest request) {
        Cuenta cuenta = cuentaService.createCuenta(request);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @PutMapping("/cuenta/{id}")
    public ResponseEntity<Cuenta> putCuenta(@PathVariable Integer id, @Valid @RequestBody CuentaRequest request) {
        Cuenta cuenta = cuentaService.updateCuenta(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @DeleteMapping("/cuenta/{id}")
    public ResponseEntity<Cuenta> deleteCuenta(@PathVariable Integer id) {
        Cuenta cuenta = cuentaService.deleteCuenta(id);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @PostMapping("/balance")
    public ResponseEntity<Cuenta> addBalance(@Valid @RequestBody BalanceRequest request){
        Cuenta cuenta = cuentaService.addBalance(request.getBalance());
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

}
