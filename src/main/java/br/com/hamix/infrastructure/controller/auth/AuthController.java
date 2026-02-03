package br.com.hamix.infrastructure.controller.auth;

import br.com.hamix.config.security.jwt.JwtService;
import br.com.hamix.infrastructure.controller.auth.dto.LoginRequest;
import br.com.hamix.infrastructure.controller.auth.dto.RegisterRequest;
import br.com.hamix.infrastructure.controller.auth.dto.RegisterResponse;
import br.com.hamix.infrastructure.controller.auth.dto.RefreshRequest;
import br.com.hamix.infrastructure.controller.auth.dto.TokenResponse;
import br.com.hamix.infrastructure.persistence.jpa.UserAccountEntity;
import br.com.hamix.infrastructure.persistence.jpa.UserAccountRepository;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e renovação de tokens")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UserAccountRepository userAccountRepository;
	private final PasswordEncoder passwordEncoder;
	private final long accessTokenMinutes;

	public AuthController(
			AuthenticationManager authenticationManager,
			JwtService jwtService,
			UserDetailsService userDetailsService,
			UserAccountRepository userAccountRepository,
			PasswordEncoder passwordEncoder,
			@Value("${jwt.access-token-minutes}") long accessTokenMinutes) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.userAccountRepository = userAccountRepository;
		this.passwordEncoder = passwordEncoder;
		this.accessTokenMinutes = accessTokenMinutes;
	}

	@PostMapping("/register")
	@Operation(summary = "Registrar usuário", description = "Cria um usuário com role padrão ROLE_USER.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Usuário criado"),
			@ApiResponse(responseCode = "409", description = "Usuário já existe"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos")
	})
	public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
		userAccountRepository.findByUsername(request.username()).ifPresent(user -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario ja existe");
		});

		UserAccountEntity saved = userAccountRepository.save(
				UserAccountEntity.builder()
						.username(request.username())
						.password(passwordEncoder.encode(request.password()))
						.roles("ROLE_USER")
						.build()
		);

		return new RegisterResponse(saved.getId(), saved.getUsername());
	}

	@PostMapping("/login")
	@Operation(summary = "Login", description = "Autentica e retorna access e refresh tokens.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Login realizado"),
			@ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos")
	})
	public TokenResponse login(@Valid @RequestBody LoginRequest request) {
		var authToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
		authenticationManager.authenticate(authToken);

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
		String accessToken = jwtService.generateAccessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		return new TokenResponse(accessToken, refreshToken, "Bearer", accessTokenMinutes * 60);
	}

	@PostMapping("/refresh")
	@Operation(summary = "Renovar token", description = "Gera novos tokens a partir do refresh token.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Tokens renovados"),
			@ApiResponse(responseCode = "401", description = "Refresh token inválido"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos")
	})
	public TokenResponse refresh(@Valid @RequestBody RefreshRequest request) {
		String refreshToken = request.refreshToken();
		String username;
		try {
			username = jwtService.extractUsername(refreshToken);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token invalido");
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (!jwtService.isTokenValid(refreshToken, userDetails) || !jwtService.isRefreshToken(refreshToken)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token invalido");
		}

		String newAccessToken = jwtService.generateAccessToken(userDetails);
		String newRefreshToken = jwtService.generateRefreshToken(userDetails);
		return new TokenResponse(newAccessToken, newRefreshToken, "Bearer", accessTokenMinutes * 60);
	}
}
