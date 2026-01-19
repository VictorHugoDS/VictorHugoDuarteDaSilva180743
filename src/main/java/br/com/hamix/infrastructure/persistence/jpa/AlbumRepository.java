package br.com.hamix.infrastructure.persistence.jpa;

import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<AlbumEntity,Integer> {
}
