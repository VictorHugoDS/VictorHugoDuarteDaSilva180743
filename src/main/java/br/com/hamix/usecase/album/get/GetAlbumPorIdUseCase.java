package br.com.hamix.usecase.album.get;

import br.com.hamix.domain.model.Album;

import java.util.Optional;

public interface GetAlbumPorIdUseCase {
    Optional<Album> findAlbumById(Integer id);
}
