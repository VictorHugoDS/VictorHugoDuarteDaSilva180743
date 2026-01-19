package br.com.hamix.usecase.album.update;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Album;
import org.springframework.stereotype.Service;

@Service
public class UpdateAlbumService implements UpdateAlbumUseCase {
    private final AlbumGateWay albumGateWay;

    public UpdateAlbumService(AlbumGateWay albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public void updateAlbum(Album reference, Integer id) {
        Album updatedReference = new Album(id,reference.nome(),reference.ano());
        albumGateWay.save(updatedReference);
    }
}
