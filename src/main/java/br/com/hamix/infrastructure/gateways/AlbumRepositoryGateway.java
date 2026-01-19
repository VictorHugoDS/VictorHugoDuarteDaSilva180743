package br.com.hamix.infrastructure.gateways;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Album;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;
import br.com.hamix.infrastructure.persistence.jpa.AlbumRepository;

import java.util.List;
import java.util.Optional;

public class AlbumRepositoryGateway implements AlbumGateWay {
    private AlbumRepository albumRepository;


    @Override
    public Album save(Album album) {
        AlbumEntity entity = AlbumEntityMapper.toEntity(album);
        AlbumEntity savedEntity = albumRepository.save(entity);
        return AlbumEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Album> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Album> findAll() {
        return List.of();
    }
}
