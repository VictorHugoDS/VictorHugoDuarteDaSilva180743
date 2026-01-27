package br.com.hamix.infrastructure.persistence.jpa.adapters;

import br.com.hamix.config.exception.custom.DataNotFoundedException;
import br.com.hamix.config.exception.custom.DatabaseException;
import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.ArtistaEntityMapper;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.PaginationMapper;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;
import br.com.hamix.infrastructure.persistence.jpa.AlbumRepository;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtisaRepositoryAdapter implements ArtistaGateway {

    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public Artista save(Artista artist) {
        ArtistaEntity entity = ArtistaEntityMapper.toEntity(artist);
        List<Integer> idsAlbumEntity = entity.getAlbuns().stream().map(AlbumEntity::getId).toList();
        try{
            List<AlbumEntity> albunsEntities = albumRepository.findAllById(idsAlbumEntity);
            entity.setAlbuns(albunsEntities);
            ArtistaEntity savedEntity = artistaRepository.save(entity);
            return ArtistaEntityMapper.toDomain(savedEntity);
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao recuperar a entidade",e);
        }

    }

    @Override
    public Artista findById(Integer id) {
        ArtistaEntity entity = artistaRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundedException("Não foi possível encontrar o Artista"));
        return ArtistaEntityMapper.toDomain(entity);

    }

    @Override
    public Page<Artista> getPage(PaginationRequest pagination, Artista toFilter) {
        Pageable pageable = PaginationMapper.toPageable(pagination);
        ArtistaEntity toFilterEntity = ArtistaEntityMapper.toEntity(toFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "origem")
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<ArtistaEntity> example = Example.of(toFilterEntity, matcher);

        try {
            return artistaRepository.findAll(example,pageable).map(ArtistaEntityMapper::toDomain);
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao recuperar a entidade",e);
        }


    }
}
