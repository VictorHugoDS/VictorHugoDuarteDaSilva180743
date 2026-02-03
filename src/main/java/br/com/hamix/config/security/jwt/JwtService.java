package br.com.hamix.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final String secret;
	private final long accessTokenMinutes;
	private final long refreshTokenMinutes;

	public JwtService(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.access-token-minutes}") long accessTokenMinutes,
			@Value("${jwt.refresh-token-minutes}") long refreshTokenMinutes) {
		this.secret = secret;
		this.accessTokenMinutes = accessTokenMinutes;
		this.refreshTokenMinutes = refreshTokenMinutes;
	}

	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(userDetails, accessTokenMinutes, "access");
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(userDetails, refreshTokenMinutes, "refresh");
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public boolean isRefreshToken(String token) {
		String type = extractAllClaims(token).get("type", String.class);
		return "refresh".equals(type);
	}

	private String generateToken(UserDetails userDetails, long minutes, String type) {
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(minutes * 60);

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claims(Map.of("type", type))
				.issuedAt(Date.from(now))
				.expiration(Date.from(expiry))
				.signWith(getSigningKey())
				.compact();
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes;
		try {
			keyBytes = Decoders.BASE64.decode(secret);
		} catch (Exception ex) {
			keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		}
		if (keyBytes.length < 32) {
			throw new IllegalStateException("jwt.secret deve ter pelo menos 32 bytes");
		}
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
