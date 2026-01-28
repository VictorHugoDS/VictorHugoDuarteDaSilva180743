package br.com.hamix.infrastructure.controller.Album.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FotoResponse {
    Integer id;
    String nome;
    String url;
}
