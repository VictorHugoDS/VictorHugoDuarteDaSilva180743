package br.com.hamix.infrastructure.gateways;

import br.com.hamix.config.exception.custom.DatabaseException;
import br.com.hamix.domain.model.Foto;
import br.com.hamix.infrastructure.gateways.mappers.FotoEntityMapper;
import br.com.hamix.infrastructure.persistence.jpa.FotoEntity;
import br.com.hamix.infrastructure.persistence.jpa.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class FotoRepositoryGateway implements br.com.hamix.domain.gateway.FotoGateway {

    @Autowired
    FotoRepository repository;

    @Override
    public Foto save(Foto foto) {
        FotoEntity entity = FotoEntityMapper.toEntity(foto);
        try{
            FotoEntity savedEntity = repository.save(entity);
            return FotoEntityMapper.toDomain(savedEntity);
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao salvar a entidade",e);
        }
    }

    @Override
    public List<Foto> findAllByIds(List<Integer> ids) {
        try {
            return repository.findAllById(ids).stream()
                    .map(FotoEntityMapper::toDomain).collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao recuperar as entidades",e);
        }
    }
}
