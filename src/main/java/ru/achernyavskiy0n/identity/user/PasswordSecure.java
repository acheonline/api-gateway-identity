package ru.achernyavskiy0n.identity.user;

public interface PasswordSecure {

	String encrypt(String password) throws PasswordSecureException;

	boolean validate(String password, String encryptedPassword) throws PasswordSecureException;
}