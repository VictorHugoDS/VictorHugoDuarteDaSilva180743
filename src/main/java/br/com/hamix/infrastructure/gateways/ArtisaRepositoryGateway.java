package br.com.hamix.infrastructure.gateways;

import br.com.hamix.domain.gateway.ArtistaGateWay;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.infrastructure.gateways.mappers.ArtistaEntityMapper;
import br.com.hamix.infrastructure.gateways.mappers.ArtistaEntityMapper;
import br.com.hamix.infrastructure.gateways.mappers.PaginationMapper;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ArtisaRepositoryGateway implements ArtistaGateWay {

    @Autowired
    ArtistaRepository artistaRepository;

    @Override
    public Artista save(Artista artist) {
        ArtistaEntity entity = ArtistaEntityMapper.toEntity(artist);
        ArtistaEntity savedEntity = artistaRepository.save(entity);
        return ArtistaEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Artista> findById(Integer id) {
        return artistaRepository.findById(id)
                .map(ArtistaEntityMapper::toDomain);
    }

    @Override
    public Page<Artista> getPage(PaginationRequest pagination, Artista toFilter) {
        Pageable pageable = PaginationMapper.toPageable(pagination);
        ArtistaEntity toFilterEntity = ArtistaEntityMapper.toEntity(toFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "origem")
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<ArtistaEntity> example = Example.of(toFilterEntity, matcher);

        return artistaRepository.findAll(example,pageable).map(ArtistaEntityMapper::toDomain);
    }
}
