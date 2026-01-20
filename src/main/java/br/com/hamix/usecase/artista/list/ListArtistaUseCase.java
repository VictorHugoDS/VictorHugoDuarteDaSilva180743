package br.com.hamix.usecase.artista.list;

import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;

public interface ListArtistaUseCase {
    PaginationResponse<Artista> listAlbunsWithPaginationAndFilters(PaginationRequest request, Artista toFilter);
}
