package br.com.hamix.infrastructure.controller.Artista.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaveArtistaRequest {

    @NotBlank
    String nome;
    String origem;
}
