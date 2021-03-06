package ru.achernyavskiy0n.identity.kafka;

import io.jsonwebtoken.jackson.io.JacksonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.achernyavskiy0n.identity.kafka.messages.AccountCreationMessage;

import java.util.HashMap;
import java.util.Map;

/** */
@Configuration
public class KafkaProducerConfig {

  @Autowired KafkaProperties kafkaProperties;

  @Bean
  public ProducerFactory<String, AccountCreationMessage> producerFactory() {
    var producer = kafkaProperties.getProducer();

    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producer.getBootstrapServers());
    props.put(ProducerConfig.CLIENT_ID_CONFIG, producer.getClientId());
    props.put(ProducerConfig.ACKS_CONFIG, producer.getAcks());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        org.springframework.kafka.support.serializer.JsonSerializer.class);
    props.put(
        org.springframework.kafka.support.serializer.JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
    props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1024);
    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, AccountCreationMessage> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
