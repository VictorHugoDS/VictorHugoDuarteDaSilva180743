package br.com.hamix.usecase.artista.criar;

import br.com.hamix.domain.model.Artista;

public interface SaveArtistaUseCase {
    Artista salvarArtista(Artista artista);
}
