package ru.achernyavskiy0n.identity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

  private static final int MAX_USERNAME_LENGTH = 256;

  private long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private Email email;
  private Phone phone;

  private User(String username, String firstName, String lastName, Email email, Phone phone) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public User(String username, String password, Email email) {
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
    return new User(username, password, Email.from(email));
  }

  public void update(
      String username, String firstName, String lastName, String email, String phone) {
    Assert.hasLength(email, "Email is required");
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = Email.from(email);
    this.phone = Phone.from(phone);
  }

  public static User from(
      String username, String firstName, String lastName, String email, String phone) {
    Assert.hasLength(username, "Username is required");
    Assert.hasLength(firstName, "First Name is required");
    Assert.hasLength(lastName, "Last Name is required");
    Assert.hasLength(email, "Email is required");
    Assert.hasLength(phone, "Phone is required");
    if (username.length() > 256) {
      throw new IllegalArgumentException(
          "The username must be a string with a maximum length of " + MAX_USERNAME_LENGTH);
    }
    return new User(username, firstName, lastName, Email.from(email), Phone.from(phone));
  }

  public void updateId(long id) {
    this.id = id;
  }
}
