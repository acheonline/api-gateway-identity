package ru.achernyavskiy0n.identity.kafka.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.achernyavskiy0n.identity.kafka.TransportProducer;
import ru.achernyavskiy0n.identity.kafka.messages.AccountCreationMessage;
import ru.achernyavskiy0n.identity.utils.StringUtils;

import java.util.UUID;

/**
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransportProducer implements TransportProducer {

  private final KafkaTemplate<String, AccountCreationMessage> kafkaTemplate;

  @Value("${identity.transport.topics.billing.create}")
  private String billingCreateTopic;

  @Override
  public void createAccount(AccountCreationMessage message) {
    var key = UUID.randomUUID().toString();
    ListenableFuture<SendResult<String, AccountCreationMessage>> future =
        kafkaTemplate.send(new ProducerRecord<>(billingCreateTopic, key, message));
    future.addCallback(getCallback(message.getUsername(), billingCreateTopic));
  }

  private ListenableFutureCallback<SendResult<String, ?>> getCallback(
      String username, String topic) {
    return new ListenableFutureCallback<>() {
      @Override
      public void onSuccess(SendResult<String, ?> result) {
        log.info(
            "Сообщение о создании аккаунта для пользователя: '{}' успешно отправлено в сервис Billing // топик: '{}'",
            username,
            result.getProducerRecord().topic());
      }

      @Override
      public void onFailure(@NotNull Throwable ex) {
        log.error("Не удалось отправить новость в топик: " + topic, ex);
      }
    };
  }
}
