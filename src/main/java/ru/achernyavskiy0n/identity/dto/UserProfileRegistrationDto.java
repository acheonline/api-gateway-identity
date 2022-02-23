package ru.achernyavskiy0n.identity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileRegistrationDto {

    private String username;
    private String email;
    private String password;
}
