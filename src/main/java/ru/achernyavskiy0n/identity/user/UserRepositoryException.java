package ru.achernyavskiy0n.identity.user;

public class UserRepositoryException extends Exception {

	public UserRepositoryException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

}