package br.com.hamix.infrastructure.persistence.jpa.adapters.mappers;

import br.com.hamix.domain.model.Regional;
import br.com.hamix.infrastructure.persistence.jpa.RegionalEntity;

public class RegionalEntityMapper {

    public static RegionalEntity toEntity(Regional domain) {
        return RegionalEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .ativo(domain.getAtivo())
                .build();
    }

    public static Regional toDomain(RegionalEntity entity) {
        return Regional.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .ativo(entity.getAtivo())
                .build();
    }
}
