package br.com.hamix.infrastructure.persistence.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ArtistaRepository extends CrudRepository<ArtistaEntity,Integer> {
}
