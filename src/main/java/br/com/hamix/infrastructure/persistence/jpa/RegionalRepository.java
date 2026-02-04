package br.com.hamix.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionalRepository extends JpaRepository<RegionalEntity, Integer> {
    List<RegionalEntity> findAllByAtivoTrue();
}