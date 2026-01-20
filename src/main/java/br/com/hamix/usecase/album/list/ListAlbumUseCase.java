package br.com.hamix.usecase.album.list;

import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;

import java.util.List;

public interface ListAlbumUseCase {
    PaginationResponse<Album> listAlbunsWithPaginationAndFilters(PaginationRequest request, Album toFilter);
}
