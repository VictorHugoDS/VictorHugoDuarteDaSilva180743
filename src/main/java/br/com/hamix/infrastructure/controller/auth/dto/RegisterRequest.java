package br.com.hamix.infrastructure.controller.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
		@NotBlank String username,
		@NotBlank String password
) {
}
