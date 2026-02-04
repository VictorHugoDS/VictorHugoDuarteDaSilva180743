package br.com.hamix.usecase.album.save;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.AlbumNotificationGateway;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

@Service
public class SaveAlbumService implements SaveAlbumUseCase {
    private final AlbumGateway albumGateWay;
    private final AlbumNotificationGateway albumNotificationGateway;

    public SaveAlbumService(AlbumGateway albumGateWay, AlbumNotificationGateway albumNotificationGateway) {
        this.albumGateWay = albumGateWay;
        this.albumNotificationGateway = albumNotificationGateway;
    }

    @Override
    public Album salvarAlbum(Album album) {
        Album savedAlbum = albumGateWay.save(album);
        albumNotificationGateway.notifyAlbumCreated(savedAlbum);
        return savedAlbum;
    }
}
