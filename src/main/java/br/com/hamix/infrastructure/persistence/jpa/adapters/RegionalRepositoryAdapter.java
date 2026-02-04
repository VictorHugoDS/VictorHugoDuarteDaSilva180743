package br.com.hamix.infrastructure.persistence.jpa.adapters;

import br.com.hamix.domain.gateway.RegionalGateway;
import br.com.hamix.domain.model.Regional;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.infrastructure.persistence.jpa.RegionalEntity;
import br.com.hamix.infrastructure.persistence.jpa.RegionalRepository;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.RegionalEntityMapper;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.PaginationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionalRepositoryAdapter implements RegionalGateway {

    @Autowired
    private RegionalRepository regionalRepository;

    @Override
    public List<Regional> findAllAtivos() {
        return regionalRepository.findAllByAtivoTrue().stream()
                .map(RegionalEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Regional> saveAll(List<Regional> regionais) {
        List<RegionalEntity> entities = regionais.stream()
                .map(RegionalEntityMapper::toEntity)
                .toList();
        return regionalRepository.saveAll(entities).stream()
                .map(RegionalEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Page<Regional> getPage(PaginationRequest pagination, Regional toFilter) {
        Pageable pageable = PaginationMapper.toPageable(pagination);
        RegionalEntity toFilterEntity = RegionalEntityMapper.toEntity(toFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "ativo")
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<RegionalEntity> example = Example.of(toFilterEntity, matcher);
        return regionalRepository.findAll(example, pageable)
                .map(RegionalEntityMapper::toDomain);
    }
}
