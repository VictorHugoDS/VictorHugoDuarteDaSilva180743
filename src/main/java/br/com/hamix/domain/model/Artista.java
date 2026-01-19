package br.com.hamix.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class Artista {
    private Integer id;
    private String nome;
    private String ano;
}
