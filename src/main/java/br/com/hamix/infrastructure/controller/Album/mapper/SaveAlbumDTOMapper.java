package br.com.hamix.infrastructure.controller.Album.mapper;

import br.com.hamix.domain.model.Album;
import br.com.hamix.infrastructure.controller.Album.dto.SaveAlbumRequest;
import br.com.hamix.infrastructure.controller.Album.dto.SaveAlbumResponse;

public class SaveAlbumDTOMapper {
    public static Album toDomain(SaveAlbumRequest request){
        return new Album(null,request.getNome(),request.getAno());
    }

    public static SaveAlbumResponse toResponse(Album domain){
        return new SaveAlbumResponse(domain.id(),domain.nome(),domain.ano());
    }
}
