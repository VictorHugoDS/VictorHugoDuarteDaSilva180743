package br.com.hamix.usecase.album.update;

import br.com.hamix.domain.model.Album;

public interface UpdateAlbumUseCase {
    void updateAlbum(Album reference, Integer id);
}
