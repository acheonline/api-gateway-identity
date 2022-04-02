package ru.achernyavskiy0n.identity.kafka.messages;

import lombok.*;
import lombok.experimental.Accessors;

/**
 */
@Data
@Accessors
@RequiredArgsConstructor
@Builder
public class AccountCreationMessage {
    private final String username;
}
