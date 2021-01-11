package fmat.aplicaciones.nube.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import fmat.aplicaciones.nube.repository.PagoRepository;
import fmat.aplicaciones.nube.model.Pago;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.request.PagoRequest;
import fmat.aplicaciones.nube.exception.InvalidOperationException;
import fmat.aplicaciones.nube.repository.CuentaRepository;
import fmat.aplicaciones.nube.enums.EstadoEnum;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public Pago makeTransaction(PagoRequest request ,Usuario user){
        Pago pago = new Pago();

        Cuenta cuentaOrigen = user.getCuenta();

        if(cuentaOrigen == null){
            throw new InvalidOperationException("La cuenta del usuario no esta registrada");
        }

        Cuenta cuentaDestino = cuentaRepository.findByNoCuenta(request.getCuentaDestino());

        if(cuentaDestino == null){
            throw new InvalidOperationException("La cuenta destino no esta registrada");
        }

        Double monto = request.getMonto();
        Date fechaRegistro = new Date();
        EstadoEnum estado = EstadoEnum.PENDIENTE;

        pago.setCuentaOrigen(cuentaOrigen);
        pago.setCuentaDestino(cuentaDestino);
        pago.setMonto(monto);
        pago.setFechaRegistro(fechaRegistro);
        pago.setEstado(estado);

        return pago;

    }

    @Transactional
    public Pago saveTransaction(Pago pago){
        pagoRepository.save(pago);

        return pago;
    }

}
