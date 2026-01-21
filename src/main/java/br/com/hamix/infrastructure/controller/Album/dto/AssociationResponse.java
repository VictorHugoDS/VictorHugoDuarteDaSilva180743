package br.com.hamix.infrastructure.controller.Album.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class AssociationResponse {
    String nome;
    String ano;
    List<ArtistaDTO> artistas;
}
