package fmat.aplicaciones.nube.rest;

import fmat.aplicaciones.nube.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CuentaRest {

    @Autowired
    private CuentaService cuentaService;


}
