package br.com.hamix.usecase.album.update;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

@Service
public class UpdateAlbumService implements UpdateAlbumUseCase {
    private final AlbumGateway albumGateWay;

    public UpdateAlbumService(AlbumGateway albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public void updateAlbum(Album reference, Integer id) {
        reference.setId(id);
        albumGateWay.save(reference);
    }
}
