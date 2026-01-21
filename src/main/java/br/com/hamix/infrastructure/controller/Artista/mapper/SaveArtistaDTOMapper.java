package br.com.hamix.infrastructure.controller.Artista.mapper;

import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SaveArtistaDTOMapper {
    public static Artista toDomain(SaveArtistaRequest request){

        List<Album> albuns = new ArrayList<>();
        if(Objects.nonNull(request.getIdsAlbum())){

            albuns = request.getIdsAlbum()
                    .stream()
                    .map(s -> Album.builder().id(s).build())
                    .collect(Collectors.toList());
        }


        return Artista.builder()
                .nome(request.getNome())
                .origem(request.getOrigem())
                .albuns(albuns)
                .build();
    }

    public static SaveArtistaResponse toResponse(Artista domain){
        return SaveArtistaResponse.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .origem(domain.getOrigem())
                .albuns(domain.getAlbuns())
                .build();
    }
}
