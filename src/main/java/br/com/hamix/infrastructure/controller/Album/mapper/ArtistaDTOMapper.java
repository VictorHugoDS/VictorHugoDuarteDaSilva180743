package br.com.hamix.infrastructure.controller.Album.mapper;

import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.controller.Album.dto.ArtistaDTO;

public class ArtistaDTOMapper {
    public static ArtistaDTO toDto(Artista entity){
        return ArtistaDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .origem(entity.getOrigem())
                .build();
    }
}
