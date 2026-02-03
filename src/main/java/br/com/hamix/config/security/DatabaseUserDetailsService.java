package br.com.hamix.config.security;

import br.com.hamix.infrastructure.persistence.jpa.UserAccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	private final UserAccountRepository userAccountRepository;

	public DatabaseUserDetailsService(UserAccountRepository userAccountRepository) {
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userAccountRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

		return new User(
				user.getUsername(),
				user.getPassword(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles())
		);
	}
}
