package br.com.hamix.infrastructure.controller.Album.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveAlbumRequest {
    @NotBlank
    String nome;
    String ano;
}
