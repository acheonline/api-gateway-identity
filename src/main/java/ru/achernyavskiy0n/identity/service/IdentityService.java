package ru.achernyavskiy0n.identity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.achernyavskiy0n.identity.kafka.TransportProducer;
import ru.achernyavskiy0n.identity.kafka.messages.AccountCreationMessage;
import ru.achernyavskiy0n.identity.security.TokenService;
import ru.achernyavskiy0n.identity.user.*;

import java.util.Optional;

/** */
@Service
@RequiredArgsConstructor
public class IdentityService {
  private final UserRepository userRepository;
  private final PasswordSecure passwordSecure;
  private final TokenService tokenService;
  private final TransportProducer producer;

  public void create(String username, String email, String password)
      throws IdentityServiceException {
    try {
      if (userRepository.userAlreadyExists(username, email)) {
        throw new IdentityServiceException(
            "User with username '" + username + "' or e-mail '" + email + "' already exists");
      } else {
        User user = User.register(username, email, passwordSecure.encrypt(password));
        userRepository.save(user);
        var message = AccountCreationMessage.builder().username(username).build();
        producer.createAccount(message);
      }
    } catch (UserRepositoryException | PasswordSecureException e) {
      throw new IdentityServiceException(e);
    }
  }

  public User findUser(String username) throws IdentityServiceException {
    try {
      Optional<User> userOptional = userRepository.findByUsername(username);

      if (userOptional.isPresent()) {
        return userOptional.get();
      } else {
        throw new IdentityServiceException("User with username: '" + username + "' not found");
      }
    } catch (UserRepositoryException e) {
      throw new IdentityServiceException(e);
    }
  }

  public void update(String username, String firstName, String lastName, String email, String phone)
      throws IdentityServiceException {
    try {
      Optional<User> userOptional = userRepository.findByUsername(username);

      if (userOptional.isPresent()) {
        User user = userOptional.get();
        user.update(username, firstName, lastName, email, phone);
        userRepository.update(user);
      } else {
        throw new IdentityServiceException("User with username: '" + username + "' not found");
      }
    } catch (UserRepositoryException e) {
      throw new IdentityServiceException(e);
    }
  }

  public String authenticate(String username, String password) throws IdentityServiceException {
    try {
      Optional<User> userOptional = userRepository.findByUsername(username);

      if (userOptional.isPresent()) {
        User user = userOptional.get();

        if (passwordSecure.validate(password, user.getPassword())) {
          return tokenService.generate(user);
        } else {
          throw new IdentityServiceException("Username or password is not valid");
        }
      } else {
        throw new IdentityServiceException("User '" + username + "' not found");
      }
    } catch (UserRepositoryException | PasswordSecureException e) {
      throw new IdentityServiceException(e);
    }
  }
}
