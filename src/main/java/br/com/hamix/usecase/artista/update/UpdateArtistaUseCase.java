package br.com.hamix.usecase.artista.update;

import br.com.hamix.domain.model.Artista;

public interface UpdateArtistaUseCase {
    void updateArtista(Artista reference, Integer id);
}
