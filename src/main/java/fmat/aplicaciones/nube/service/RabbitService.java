package fmat.aplicaciones.nube.service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import fmat.aplicaciones.nube.dto.PagoDTO;
import fmat.aplicaciones.nube.dto.RegistroDTO;
import fmat.aplicaciones.nube.exception.InvalidOperationException;

@Service
public class RabbitService {
  @Autowired
  private AmqpTemplate rabbitTemplate;

  private Logger logger = LogManager.getLogger(this.getClass());

  @Value("${sample.rabbitmq.exchange}")
  String exchange;
  @Value("${sample.rabbitmq.routingkey}")
  String routingkey;

  @Scheduled
  public void send(PagoDTO pago) {
    // String message = pago.toString();
    Gson g = new Gson();
    String message = g.toJson(pago);

    rabbitTemplate.convertAndSend(exchange, routingkey, message);

    //create csv
    String filename = pago.getIdPago()+".csv";
    try {
      File file = new File(filename);
      if (file.createNewFile()) {
        System.out.println("File created: " + file.getName());
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("Id de pago");;
        csvWriter.append(",");
        csvWriter.append("Monto");;
        csvWriter.append(",");
        csvWriter.append("Cuenta de destino");;
        csvWriter.append(",");
        csvWriter.append("Cuenta origen");
        csvWriter.append(",");
        csvWriter.append("Fecha de Registro");
        csvWriter.append(",");
        csvWriter.append("Fecha de procesamiento");;
        csvWriter.append(",");
        csvWriter.append("Estado");;
        csvWriter.append("\n");
        csvWriter.append(pago.getIdPago().toString());
        csvWriter.append(",");
        csvWriter.append(pago.getMonto().toString());;
        csvWriter.append(",");
        csvWriter.append(pago.getCuentaDestino());
        csvWriter.append(",");
        csvWriter.append(pago.getCuentaOrigen());
        csvWriter.append(",");
        csvWriter.append(pago.getFechaRegistro());
        csvWriter.append(",");
        csvWriter.append(pago.getFechaProcesa());;
        csvWriter.append(",");
        csvWriter.append(pago.getEstado());;
        csvWriter.append("\n");
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Successfully wrote to the file.");
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }


    System.out.println("Mensaje enviado: " + message);
    // logger.info("Mensaje enviado:= ", message);
  }

  @Scheduled
  public RegistroDTO sendTransfer(PagoDTO pago) {

    Gson g = new Gson();

    String request = g.toJson(pago);
    ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
    };
    String response = rabbitTemplate.convertSendAndReceiveAsType(exchange, routingkey, request, responseType);

    if (response == null) {
      logger.error("Mensaje no enviado");
      throw new InvalidOperationException("El mensaje no ha podido ser enviado");
    }

    RegistroDTO registrationDto = g.fromJson(response, RegistroDTO.class);

    logger.info("Mensaje enviado con exito");
    return registrationDto;
  }
}