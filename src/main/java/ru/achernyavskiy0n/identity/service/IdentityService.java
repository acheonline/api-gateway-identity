package ru.achernyavskiy0n.identity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.achernyavskiy0n.identity.persistence.UserRepository;
import ru.achernyavskiy0n.identity.security.TokenService;
import ru.achernyavskiy0n.identity.user.PasswordSecure;
import ru.achernyavskiy0n.identity.user.PasswordSecureException;
import ru.achernyavskiy0n.identity.user.User;

import java.util.Optional;

/** */
@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityService {
  private final UserRepository userRepository;
  private final PasswordSecure passwordSecure;
  private final TokenService tokenService;

  public void create(String username, String email, String password)
      throws IdentityServiceException {
    try {
      if (userRepository.findByUsernameAndEmail(username, email).isPresent()) {
        throw new IdentityServiceException(
            "User with username '" + username + "' or e-mail '" + email + "' already exists");
      } else {
        User user = User.register(username, email, passwordSecure.encrypt(password));
        log.info("User with USERNAME: '{}' is registered.", user.getUsername());
        log.debug("User registered: {}", user);
        userRepository.save(user);
      }
    } catch (PasswordSecureException e) {
      throw new IdentityServiceException(e);
    }
  }

  public String authenticate(String username, String password) throws IdentityServiceException {
    try {
      Optional<User> userOptional = userRepository.findByUsername(username);

      if (userOptional.isPresent()) {
        User user = userOptional.get();

        if (passwordSecure.validate(password, user.getPassword())) {
          log.info("User with UUID: {} is authenticated.", user.getId());
          log.debug("User authenticated: {}", user);
          return tokenService.generate(user);
        } else {
          throw new IdentityServiceException("Username or password is not valid");
        }
      } else {
        throw new IdentityServiceException("User '" + username + "' not found");
      }
    } catch (PasswordSecureException e) {
      throw new IdentityServiceException(e);
    }
  }
}
