package br.com.hamix.usecase.album.get;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetAlbumService implements GetAlbumUseCase {
    private final AlbumGateWay albumGateWay;

    public GetAlbumService(AlbumGateWay albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public Album findAlbumById(Integer id) {
        return albumGateWay.findById(id);
    }
}
