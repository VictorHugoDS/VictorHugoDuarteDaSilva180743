package br.com.hamix.usecase.artista.get;

import br.com.hamix.domain.model.Artista;

import java.util.Optional;

public interface GetArtistaPorIdUseCase {
    Artista findArtistaById(Integer id);
}
