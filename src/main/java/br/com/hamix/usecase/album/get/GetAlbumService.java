package br.com.hamix.usecase.album.get;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

@Service
public class GetAlbumService implements GetAlbumUseCase {
    private final AlbumGateway albumGateWay;

    public GetAlbumService(AlbumGateway albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public Album findAlbumById(Integer id) {
        return albumGateWay.findById(id);
    }
}
