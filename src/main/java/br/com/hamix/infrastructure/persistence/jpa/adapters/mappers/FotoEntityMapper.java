package br.com.hamix.infrastructure.persistence.jpa.adapters.mappers;

import br.com.hamix.domain.model.Foto;
import br.com.hamix.infrastructure.persistence.jpa.FotoEntity;

public class FotoEntityMapper {
    public static FotoEntity toEntity(Foto domain){
        return FotoEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .album(AlbumEntityMapper.toEntity(domain.getAlbum()))
                .build();
    }
    public static Foto toDomain(FotoEntity entity){
        return Foto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .album(AlbumEntityMapper.toDomain(entity.getAlbum()))
                .build();
    }
}
