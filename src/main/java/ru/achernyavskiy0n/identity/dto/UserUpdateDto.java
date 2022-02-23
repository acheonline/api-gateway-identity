package ru.achernyavskiy0n.identity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateDto {

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
}