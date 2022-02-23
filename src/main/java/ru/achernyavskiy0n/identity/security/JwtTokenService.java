package ru.achernyavskiy0n.identity.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.achernyavskiy0n.identity.user.User;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenService implements TokenService {

	@Value("${jwt.privateKey}")
	private String privateKeyValue;
	@Value("${jwt.publicKey}")
	private String publicKeyValue;

	private PrivateKey privateKey;
	private PublicKey publicKey;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
		privateKey = PemKeyUtils.privateKeyFromString(privateKeyValue);
		publicKey = PemKeyUtils.publicKeyFromString(publicKeyValue);
	}

	@Override
	public String generate(User user) {
		Date date = Date.from(LocalDateTime.now().plusHours(12).atZone(ZoneId.systemDefault()).toInstant());
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(date)
				.setHeaderParam("alg", "RS256")
				.signWith(privateKey, SignatureAlgorithm.RS256)
				.compact();

	}

	@Override
	public String validate(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(publicKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

}