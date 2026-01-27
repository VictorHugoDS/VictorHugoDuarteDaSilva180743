package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import org.springframework.data.domain.Page;

public interface ArtistaGateway {
    Artista save(Artista artist);
    Artista findById(Integer id);
    Page<Artista> getPage(PaginationRequest pagination, Artista toFilter);
}
