package br.com.hamix.infrastructure.controller.Album.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ArtistaDTO {
    Integer id;
    String nome;
    String origem;
}
