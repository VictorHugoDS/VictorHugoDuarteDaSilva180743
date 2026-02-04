package br.com.hamix.infrastructure.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AlbumCreatedMessage {
    private Integer id;
    private String nome;
    private String ano;
}
