package br.com.hamix.usecase.album.save;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

@Service
public class SaveAlbumService implements SaveAlbumUseCase {
    private final AlbumGateway albumGateWay;

    public SaveAlbumService(AlbumGateway albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public Album salvarAlbum(Album album) {
        return albumGateWay.save(album);
    }
}
