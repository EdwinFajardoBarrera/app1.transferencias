package fmat.aplicaciones.nube.service;

import java.util.Random;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
  @Autowired
  private AmqpTemplate rabbitTemplate;

  @Value("${sample.rabbitmq.exchange}")
  String exchange;
  @Value("${sample.rabbitmq.routingkey}")
  String routingkey;

  @Scheduled
  public void send(String message) {
    rabbitTemplate.convertAndSend(exchange, routingkey, message);
    System.out.println("Mensaje enviado:= " + message + " ");
  }
}