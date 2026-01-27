package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import org.springframework.data.domain.Page;


import java.util.List;

public interface AlbumGateway {
    Album save(Album album);
    Album findById(Integer id);
    Page<Album> getPage(PaginationRequest pagination, Album toFilter);
    Album associateArtistas(Integer idAlbum, List<Artista> artistasList);
    List<Artista> getAssociacaoById(Integer id);
}
