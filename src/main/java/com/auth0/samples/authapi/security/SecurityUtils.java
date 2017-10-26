package com.auth0.samples.authapi.security;

import com.auth0.samples.authapi.user.ApplicationUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecurityUtils {
	//@Value("${security.jwt.secret}")
	public static String SECRET;
//	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";

	public static String generateToken(Long userId, String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId",userId);
		claims.put("username", username);

		return Jwts.builder()
				.setSubject(username)
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
				.compact();
	}

	@Value("${security.jwt.secret}")
	public void setSECRET(String SECRET) {
		SecurityUtils.SECRET = SECRET;
		System.out.println(SecurityUtils.SECRET);
	}
}
