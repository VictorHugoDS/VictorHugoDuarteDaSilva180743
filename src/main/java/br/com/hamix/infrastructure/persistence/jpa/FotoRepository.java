package br.com.hamix.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<FotoEntity,Integer> {
}
