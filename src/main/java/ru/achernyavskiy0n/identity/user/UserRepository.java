package ru.achernyavskiy0n.identity.user;


import java.util.Optional;

public interface UserRepository {

	void save(User user) throws UserRepositoryException;

	void update(User user) throws UserRepositoryException;

	Optional<User> findById(long userId) throws UserRepositoryException;

	Optional<User> findByUsername(String username) throws UserRepositoryException;

	boolean userAlreadyExists(String username, String email) throws UserRepositoryException;

}