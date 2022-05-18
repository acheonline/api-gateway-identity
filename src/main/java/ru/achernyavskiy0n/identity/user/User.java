package ru.achernyavskiy0n.identity.user;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  private static final int MAX_USERNAME_LENGTH = 256;

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String username;
  private String password;
  private String email;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public static User register(String username, String email, String password) {
    Assert.hasLength(username, "Username is required");
    Assert.hasLength(email, "Email is required");
    Assert.hasLength(password, "Password is required");
    if (username.length() > MAX_USERNAME_LENGTH) {
      throw new IllegalArgumentException(
          "The username must be a string with a maximum length of " + MAX_USERNAME_LENGTH);
    }
    return new User(username, password, email);
  }
}
