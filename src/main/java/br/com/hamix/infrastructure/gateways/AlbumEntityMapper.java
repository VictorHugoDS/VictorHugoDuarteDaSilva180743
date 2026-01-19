package br.com.hamix.infrastructure.gateways;

import br.com.hamix.domain.model.Album;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;

public class AlbumEntityMapper {
    public static AlbumEntity toEntity(Album domain){
        return AlbumEntity.builder()
                .id(domain.id())
                .nome(domain.nome())
                .ano(domain.ano())
                .build();
    }
    public static Album toDomain(AlbumEntity entity){
        return new Album(entity.getId(),entity.getNome(),entity.getAno());
    }
}
