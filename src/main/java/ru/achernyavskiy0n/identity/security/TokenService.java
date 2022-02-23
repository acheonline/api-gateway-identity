
package ru.achernyavskiy0n.identity.security;

import ru.achernyavskiy0n.identity.user.User;

public interface TokenService {

	String generate(User user);

	String validate(String token);

}