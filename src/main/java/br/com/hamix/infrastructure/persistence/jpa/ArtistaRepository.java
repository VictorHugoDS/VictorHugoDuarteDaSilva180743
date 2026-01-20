package br.com.hamix.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ArtistaRepository extends JpaRepository<ArtistaEntity,Integer> {
}
