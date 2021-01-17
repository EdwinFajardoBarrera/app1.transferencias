package fmat.aplicaciones.nube.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fmat.aplicaciones.nube.dto.PagoDTO;
import fmat.aplicaciones.nube.dto.RegistroDTO;
import fmat.aplicaciones.nube.model.Pago;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.model.request.PagoRequest;
import fmat.aplicaciones.nube.service.PagoService;
import fmat.aplicaciones.nube.service.RabbitService;
import fmat.aplicaciones.nube.util.Util;

@RestController
@RequestMapping("/api")
public class PagoRest {
    @Autowired
    private PagoService pagoService;

    @Autowired
    private RabbitService rs;

    private Logger logger = LogManager.getLogger(this.getClass());

    @PostMapping("/pago")
    public ResponseEntity<PagoDTO> postRegister(@RequestBody @Valid PagoRequest pagoRequest) throws URISyntaxException {
        Pago pago = pagoService.makeTransaction(pagoRequest);

        String fechaRegistro = Util.getFormattedDate(pago.getFechaRegistro());

        PagoDTO pagoDto = new PagoDTO();
        pagoDto.setIdPago(pago.getId());
        pagoDto.setMonto(pagoRequest.getMonto());
        pagoDto.setCuentaOrigen(pago.getCuentaOrigen().getNoCuenta());
        pagoDto.setCuentaDestino(pago.getCuentaDestino().getNoCuenta());
        pagoDto.setEstado(pago.getEstado().name());
        pagoDto.setFechaRegistro(fechaRegistro);

        rs.send(pagoDto);

        return ResponseEntity.status(HttpStatus.OK).body(pagoDto);
    }

    @GetMapping("/transacciones")
    public ResponseEntity<List<Pago>> getUserTransactions() {
        List<Pago> transacciones = pagoService.getUserTransactions();

        return ResponseEntity.status(HttpStatus.OK).body(transacciones);

        // return pagoService.getUserTransactions(cuentaOrigen)
    }
}
