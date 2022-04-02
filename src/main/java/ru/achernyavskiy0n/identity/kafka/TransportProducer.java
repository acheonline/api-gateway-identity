package ru.achernyavskiy0n.identity.kafka;

import ru.achernyavskiy0n.identity.kafka.messages.AccountCreationMessage;

/**
 */
public interface TransportProducer {
    void createAccount(AccountCreationMessage message);
}
