package fmat.aplicaciones.nube.service;

import java.util.Random;

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
import fmat.aplicaciones.nube.model.Pago;

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