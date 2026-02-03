package br.com.hamix.infrastructure.controller.auth.dto;

public record TokenResponse(
		String accessToken,
		String refreshToken,
		String tokenType,
		long expiresInSeconds
) {
}
