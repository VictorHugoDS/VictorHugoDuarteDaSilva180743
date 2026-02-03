package br.com.hamix.infrastructure.persistence.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Integer> {
	Optional<UserAccountEntity> findByUsername(String username);
}
