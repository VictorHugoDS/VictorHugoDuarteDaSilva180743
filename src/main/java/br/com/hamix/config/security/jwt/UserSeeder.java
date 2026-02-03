package br.com.hamix.config.security.jwt;

import br.com.hamix.infrastructure.persistence.jpa.UserAccountEntity;
import br.com.hamix.infrastructure.persistence.jpa.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements ApplicationRunner {

	private final UserAccountRepository userAccountRepository;
	private final PasswordEncoder passwordEncoder;
	private final String defaultUsername;
	private final String defaultPassword;

	public UserSeeder(
			UserAccountRepository userAccountRepository,
			PasswordEncoder passwordEncoder,
			@Value("${spring.security.user.name}") String defaultUsername,
			@Value("${spring.security.user.password}") String defaultPassword) {
		this.userAccountRepository = userAccountRepository;
		this.passwordEncoder = passwordEncoder;
		this.defaultUsername = defaultUsername;
		this.defaultPassword = defaultPassword;
	}

	@Override
	public void run(ApplicationArguments args) {
		userAccountRepository.findByUsername(defaultUsername).ifPresentOrElse(
				user -> { },
				() -> userAccountRepository.save(
						UserAccountEntity.builder()
								.username(defaultUsername)
								.password(passwordEncoder.encode(defaultPassword))
								.roles("ROLE_USER")
								.build()
				)
		);
	}
}
