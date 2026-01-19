package br.com.hamix.usecase.album.save;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

@Service
public class SaveAlbumService implements SaveAlbumUseCase {
    private final AlbumGateWay albumGateWay;

    public SaveAlbumService(AlbumGateWay albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public Album salvarAlbum(Album album) {
        return albumGateWay.save(album);
    }
}
