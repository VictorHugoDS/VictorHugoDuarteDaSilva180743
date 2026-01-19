package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumGateWay {
    Album save(Album album);
    Optional<Album> findById(Integer id);
    List<Album> findAll();
}
