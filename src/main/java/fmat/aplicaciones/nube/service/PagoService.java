package fmat.aplicaciones.nube.service;

import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import fmat.aplicaciones.nube.repository.PagoRepository;
import fmat.aplicaciones.nube.model.Pago;
import fmat.aplicaciones.nube.model.Usuario;
import fmat.aplicaciones.nube.model.Cuenta;
import fmat.aplicaciones.nube.model.request.PagoRequest;
import fmat.aplicaciones.nube.exception.InvalidOperationException;
import fmat.aplicaciones.nube.repository.CuentaRepository;
import fmat.aplicaciones.nube.dto.PagoCsvDTO;
import fmat.aplicaciones.nube.dto.PagoDTO;
import fmat.aplicaciones.nube.enums.EstadoEnum;
import fmat.aplicaciones.nube.util.Util;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private RabbitService rs;

    @Transactional
    public Pago makeTransaction(PagoRequest request) {
        Pago pago = new Pago();
        Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cuenta cuentaOrigen = user.getCuenta();

        if (cuentaOrigen == null) {
            throw new InvalidOperationException("La cuenta del usuario no esta registrada");
        }

        Cuenta cuentaDestino = cuentaRepository.findByNoCuenta(request.getCuentaDestino());

        if (cuentaDestino == null) {
            throw new InvalidOperationException("La cuenta destino no esta registrada");
        }

        if (cuentaOrigen.getNoCuenta().equals(cuentaDestino.getNoCuenta())) {
            throw new InvalidOperationException("La cuenta origen no puede ser igual a la cuenta destino");
        }

        // Double monto = request.getMonto();
        BigDecimal monto = request.getMonto();
        Date fechaRegistro = new Date();
        EstadoEnum estado = EstadoEnum.PENDIENTE;

        pago.setCuentaOrigen(cuentaOrigen);
        pago.setCuentaDestino(cuentaDestino);
        pago.setMonto(monto);
        pago.setFechaRegistro(fechaRegistro);
        pago.setEstado(estado);

        pagoRepository.save(pago);

        return pago;

    }

    public List<Pago> makeCsvTransaction(List<PagoCsvDTO> pagos) {
        List<Pago> payments = new ArrayList<>();
        Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cuenta cuentaOrigen = user.getCuenta();

        if (cuentaOrigen == null) {
            throw new InvalidOperationException("La cuenta del usuario no esta registrada");
        }

        for (PagoCsvDTO pago : pagos) {
            Pago item = new Pago();
            Cuenta cuentaDestino = cuentaRepository.findByNoCuenta(pago.getCuentaDestino());

            if (cuentaDestino == null) {
                throw new InvalidOperationException(
                        "La cuenta destino" + pago.getCuentaDestino() + "no esta registrada en el sistema");
            }
            if (cuentaOrigen.getNoCuenta().equals(cuentaDestino.getNoCuenta())) {
                throw new InvalidOperationException("La cuenta origen no puede ser igual a la cuenta destino");
            }

            BigDecimal monto = pago.getMonto();
            Date fechaRegistro = new Date();
            EstadoEnum estado = EstadoEnum.PENDIENTE;

            item.setCuentaOrigen(cuentaOrigen);
            item.setCuentaDestino(cuentaDestino);
            item.setMonto(monto);
            item.setFechaRegistro(fechaRegistro);
            item.setEstado(estado);

            pagoRepository.save(item);
            payments.add(item);
        }
        return payments;
    }

    public List<Pago> getUserTransactions() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cuenta cuenta = usuario.getCuenta();

        return pagoRepository.findByCuentaOrigen(cuenta);
    }

    public PagoDTO sendPayment(Pago pago) {

        String fechaRegistro = Util.getFormattedDate(pago.getFechaRegistro());

        PagoDTO pagoDto = new PagoDTO();
        pagoDto.setIdPago(pago.getId());
        pagoDto.setMonto(pago.getMonto());
        pagoDto.setCuentaOrigen(pago.getCuentaOrigen().getNoCuenta());
        pagoDto.setCuentaDestino(pago.getCuentaDestino().getNoCuenta());
        pagoDto.setEstado(pago.getEstado().name());
        pagoDto.setFechaRegistro(fechaRegistro);

        rs.send(pagoDto);

        return pagoDto;
    }

    public List<PagoDTO> sendPayments(List<Pago> pagos) {
        List<PagoDTO> payments = new ArrayList<>();

        for (Pago payment : pagos) {
            PagoDTO item = this.sendPayment(payment);

            payments.add(item);
        }
        return payments;
    }

    public List<Pago> getCsvPagos(MultipartFile file) {
        List<Pago> pagos = new ArrayList<>();

        // Leer archivo
        if (file.isEmpty()) {
            throw new InvalidOperationException("Debe seleccionar un archivo CSV a subir");
        }

        // parse CSV file to create a list of `User` objects
        try {

            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            // create csv bean reader
            CsvToBean<PagoCsvDTO> csvToBean = new CsvToBeanBuilder(reader).withType(PagoCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true).build();

            // convert `CsvToBean` object to list of users
            List<PagoCsvDTO> payments = csvToBean.parse();

            pagos = this.makeCsvTransaction(payments);

        } catch (InvalidOperationException e) {
            throw new InvalidOperationException(e.getMessage());
        } catch (Exception ex) {
            throw new InvalidOperationException("Ocurrió un error al leer el archivo CSV");
        }

        return pagos;

    }

}
