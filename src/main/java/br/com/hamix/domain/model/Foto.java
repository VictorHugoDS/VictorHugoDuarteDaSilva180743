package br.com.hamix.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class Foto {
    Integer id;
    String nome;
    String url;
    Album album;
}
