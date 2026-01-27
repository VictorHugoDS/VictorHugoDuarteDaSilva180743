package br.com.hamix.usecase.album.associate;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociateArtistsToAlbumService implements AssociateArtistsToAlbumUseCase{
    private final AlbumGateway albumGateWay;
    private final ArtistaGateway artistaGateWay;

    public AssociateArtistsToAlbumService(AlbumGateway albumGateWay, ArtistaGateway artistaGateWay) {
        this.albumGateWay = albumGateWay;
        this.artistaGateWay = artistaGateWay;
    }

    @Override
    public Album associate(Integer albumId, List<Integer> artistIds) {
        List<Artista> artistas = artistIds.stream().map(artistaGateWay::findById).toList();
        return albumGateWay.associateArtistas(albumId,artistas);
    }
}
