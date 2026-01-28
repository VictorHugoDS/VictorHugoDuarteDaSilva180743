package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Foto;

import java.util.List;

public interface FotoGateway {
    Foto save(Foto foto);
    List<Foto> findByAlbum(Album album);
}
