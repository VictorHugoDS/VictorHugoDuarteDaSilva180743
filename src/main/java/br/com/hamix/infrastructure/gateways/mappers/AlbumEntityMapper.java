package br.com.hamix.infrastructure.gateways.mappers;

import br.com.hamix.domain.model.Album;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;

public class AlbumEntityMapper {
    public static AlbumEntity toEntity(Album domain){
        return AlbumEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .ano(domain.getAno())
                .build();
    }
    public static Album toDomain(AlbumEntity entity){
        return Album.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .ano(entity.getAno())
                .build();
    }
}
