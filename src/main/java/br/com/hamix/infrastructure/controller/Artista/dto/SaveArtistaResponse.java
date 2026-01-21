package br.com.hamix.infrastructure.controller.Artista.dto;

import br.com.hamix.domain.model.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class SaveArtistaResponse {
    Integer id;
    String nome;
    String origem;
    List<Album> albuns;
}
