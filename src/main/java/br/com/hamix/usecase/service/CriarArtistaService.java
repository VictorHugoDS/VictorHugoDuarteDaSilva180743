package br.com.hamix.usecase.service;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Album;
import br.com.hamix.usecase.CriarArtistaUseCase;

public class CriarArtistaService implements CriarArtistaUseCase {
    private AlbumGateWay albumGateWay;

    public CriarArtistaService(AlbumGateWay albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public Album salvarAlbum(Album album) {
        return albumGateWay.save(album);
    }
}
