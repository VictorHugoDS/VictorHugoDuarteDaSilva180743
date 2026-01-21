package br.com.hamix.infrastructure.gateways.mappers;


import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArtistaEntityMapper {
    public static ArtistaEntity toEntity(Artista domain){

        List<AlbumEntity> albuns = new ArrayList<>();
        if(Objects.nonNull(domain.getAlbuns())){
            albuns = domain.getAlbuns().stream().map(AlbumEntityMapper::toEntity).toList();
        }
        return ArtistaEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .origem(domain.getOrigem())
                .albuns(albuns)
                .build();
    }
    public static Artista toDomain(ArtistaEntity entity){
        return Artista.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .origem(entity.getOrigem())
                .albuns(entity.getAlbuns().stream().map(AlbumEntityMapper::toDomain).toList())
                .build();
    }
}
