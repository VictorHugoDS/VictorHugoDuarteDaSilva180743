package br.com.hamix.infrastructure.controller.Album.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FotoResponse {
    Integer id;
    String nome;
    String url;
}
