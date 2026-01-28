package br.com.hamix.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<FotoEntity,Integer> {
    List<FotoEntity> findByAlbum(AlbumEntity album);
}
