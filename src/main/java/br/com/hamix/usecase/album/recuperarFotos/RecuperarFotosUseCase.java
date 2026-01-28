package br.com.hamix.usecase.album.recuperarFotos;


import br.com.hamix.domain.model.Foto;

import java.util.List;

public interface RecuperarFotosUseCase {
    List<Foto> recuperarFotosDeAlbum(Integer albumId);
}
