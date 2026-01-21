package br.com.hamix.usecase.album.getAssociation;

import br.com.hamix.domain.model.Artista;

import java.util.List;

public interface GetAssociationUseCase {
    List<Artista> getAssociacaoById(Integer id);
}
