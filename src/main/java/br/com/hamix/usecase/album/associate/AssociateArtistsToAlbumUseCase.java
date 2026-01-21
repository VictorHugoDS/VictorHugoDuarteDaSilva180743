package br.com.hamix.usecase.album.associate;

import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;

import java.util.List;

public interface AssociateArtistsToAlbumUseCase {
    Album associate(Integer albumId, List<Integer> artistIds);
}
