package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.pagination.PaginationRequest;
import org.springframework.data.domain.Page;


import java.util.Optional;

public interface AlbumGateWay {
    Album save(Album album);
    Optional<Album> findById(Integer id);
    Page<Album> getPage(PaginationRequest pagination, Album toFilter);
}
