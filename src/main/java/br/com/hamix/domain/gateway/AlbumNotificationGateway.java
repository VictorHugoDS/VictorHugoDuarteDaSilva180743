package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Album;

public interface AlbumNotificationGateway {
    void notifyAlbumCreated(Album album);
}
