package br.com.hamix.usecase.artista.list;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListArtistaService implements ListArtistaUseCase {
    private final ArtistaGateway artistaGateWay;

    public ListArtistaService(ArtistaGateway artistaGateWay) {
        this.artistaGateWay = artistaGateWay;
    }

    @Override
    public PaginationResponse<Artista> listAlbunsWithPaginationAndFilters(PaginationRequest request, Artista toFilter) {
        Page<Artista> page =artistaGateWay.getPage(request,toFilter);
        return new PaginationResponse<>(
                page.toList(),
                String.valueOf(page.getNumber()),
                String.valueOf(page.getSize()),
                String.valueOf(page.getTotalPages()),
                String.valueOf(page.getTotalElements())
        );
    }
}
