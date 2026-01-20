package br.com.hamix.infrastructure.controller.Artista.mapper;

import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaResponse;

public class SaveArtistaDTOMapper {
    public static Artista toDomain(SaveArtistaRequest request){
        return new Artista(null,request.getNome(),request.getOrigem());
    }

    public static SaveArtistaResponse toResponse(Artista domain){
        return new SaveArtistaResponse(domain.id(),domain.nome(),domain.origem());
    }
}
