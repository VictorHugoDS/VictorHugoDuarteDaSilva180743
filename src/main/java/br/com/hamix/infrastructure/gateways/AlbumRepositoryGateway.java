package br.com.hamix.infrastructure.gateways;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.infrastructure.gateways.mappers.AlbumEntityMapper;
import br.com.hamix.infrastructure.gateways.mappers.PaginationMapper;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;
import br.com.hamix.infrastructure.persistence.jpa.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumRepositoryGateway implements AlbumGateWay {
    @Autowired
    private AlbumRepository albumRepository;


    @Override
    public Album save(Album album) {
        AlbumEntity entity = AlbumEntityMapper.toEntity(album);
        AlbumEntity savedEntity = albumRepository.save(entity);
        return AlbumEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Album> findById(Integer id) {
        return albumRepository.findById(id)
                .map(AlbumEntityMapper::toDomain);
    }

    @Override
    public Page<Album> getPage(PaginationRequest pagination, Album toFilter) {
        Pageable pageable = PaginationMapper.toPageable(pagination);
        AlbumEntity toFilterEntity = AlbumEntityMapper.toEntity(toFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "ano")
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<AlbumEntity> example = Example.of(toFilterEntity, matcher);

        return albumRepository.findAll(example,pageable).map(AlbumEntityMapper::toDomain);
    }

}
