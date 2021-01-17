package fmat.aplicaciones.nube.rest;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<RegistroDTO> postRegister(@RequestBody @Valid PagoRequest pagoRequest)
            throws URISyntaxException {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pago pago = pagoService.makeTransaction(pagoRequest, usuario);
        logger.info(pago.toString());
        String fechaRegistro = Util.getFormattedDate(pago.getFechaRegistro());

        PagoDTO pagoDto = new PagoDTO();
        pagoDto.setIdPago(pago.getId());
        pagoDto.setMonto(pagoRequest.getMonto());
        pagoDto.setCuentaOrigen(pago.getCuentaOrigen().getNoCuenta());
        pagoDto.setCuentaOrigen(pago.getCuentaDestino().getNoCuenta());
        pagoDto.setEstado(pago.getEstado().name());
        pagoDto.setFechaRegistro(fechaRegistro);

        RegistroDTO registro = rs.sendTransfer(pagoDto);

        return ResponseEntity.status(HttpStatus.OK).body(registro);
    }
}
