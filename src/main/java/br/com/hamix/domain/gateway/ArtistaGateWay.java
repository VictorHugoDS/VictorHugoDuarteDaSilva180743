package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Artista;

import java.util.List;
import java.util.Optional;

public interface ArtistaGateWay {
    Artista save(Artista artist);
    Optional<Artista> findById(Integer id);
    List<Artista> findAll();
}
