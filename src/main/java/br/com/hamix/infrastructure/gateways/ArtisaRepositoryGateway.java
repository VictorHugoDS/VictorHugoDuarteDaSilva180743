package br.com.hamix.infrastructure.gateways;

import br.com.hamix.domain.gateway.ArtistaGateWay;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.gateways.mappers.ArtistaEntityMapper;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Artista> findAll() {
        return List.of();
    }
}
