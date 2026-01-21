package br.com.hamix.infrastructure.controller.Artista.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SaveArtistaRequest {

    @NotBlank
    String nome;
    @Nullable
    String origem;
    @Nullable
    List<Integer> idsAlbum;
}
