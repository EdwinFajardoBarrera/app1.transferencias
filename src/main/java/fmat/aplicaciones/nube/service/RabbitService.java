package fmat.aplicaciones.nube.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fmat.aplicaciones.nube.model.Pago;

@Service
public class RabbitService {
  @Autowired
  private AmqpTemplate rabbitTemplate;

  @Value("${sample.rabbitmq.exchange}")
  String exchange;
  @Value("${sample.rabbitmq.routingkey}")
  String routingkey;

  @Scheduled
  public void send(Pago pago) {
    rabbitTemplate.convertAndSend(exchange, routingkey, pago.toString());
    System.out.println("Detalles de pago:= " + pago.toString() + " ");
  }
}