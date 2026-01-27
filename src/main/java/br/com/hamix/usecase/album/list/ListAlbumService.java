package br.com.hamix.usecase.album.list;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListAlbumService implements ListAlbumUseCase {
    private final AlbumGateway albumGateWay;

    public ListAlbumService(AlbumGateway albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public PaginationResponse<Album> listAlbunsWithPaginationAndFilters(PaginationRequest request, Album toFilter) {
        Page<Album> page =albumGateWay.getPage(request,toFilter);
        return new PaginationResponse<>(
                page.toList(),
                String.valueOf(page.getNumber()),
                String.valueOf(page.getSize()),
                String.valueOf(page.getTotalPages()),
                String.valueOf(page.getTotalElements())
        );
    }
}
