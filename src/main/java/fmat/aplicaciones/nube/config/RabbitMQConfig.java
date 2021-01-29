package fmat.aplicaciones.nube.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.FanoutExchange;

@Configuration
public class RabbitMQConfig {

  @Value("${sample.rabbitmq.exchange}")
  String exchange;
  @Value("${sample.rabbitmq.routingkey}")
  String routingkey;
  @Value("${sample.rabbitmq.queue}")
  String queueName;

  private String incomingQueue = "DLQ_queue";
  private String dlqEx = "dead.letter.test";

  @Bean
  Queue queue() {
    Map<String, Object> args = new HashMap<String, Object>();
    // The default exchange
    args.put("x-dead-letter-exchange",dlqEx);
    // Route to the incoming queue when the TTL occurs
    args.put("x-dead-letter-routing-key", incomingQueue);
    // TTL 5 seconds
    args.put("x-message-ttl", 5000);
    return new Queue(queueName, false, false , false, args);
  }
  
  @Bean
  Queue dlqQueue(){
    return new Queue(incomingQueue);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  DirectExchange dlqExhange(){
    return new DirectExchange(dlqEx);  
  }

  @Bean
  Binding bindingQueue(TopicExchange exchange) {
    return BindingBuilder.bind(queue()).to(exchange).with(routingkey);
  }

  @Bean
  Binding bindingDLQ(DirectExchange exchange) {
    return BindingBuilder.bind(dlqQueue()).to(exchange).with(routingkey);
  }

  // @Bean
  // public AsyncRabbitTemplate asyncRabbitTemplate(
  // RabbitTemplate rabbitTemplate){
  // return new AsyncRabbitTemplate(rabbitTemplate);
  // }

  // @Bean
  // public MessageConverter jsonMessageConverter() {
  // return new Jackson2JsonMessageConverter();
  // }

  @Bean
  public AmqpTemplate container(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    // rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }
}