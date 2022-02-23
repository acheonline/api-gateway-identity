package ru.achernyavskiy0n.identity.user;

public class PasswordSecureException extends Exception {

	public PasswordSecureException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}