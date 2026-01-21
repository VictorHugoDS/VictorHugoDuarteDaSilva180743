package br.com.hamix.usecase.album.associate;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.gateway.ArtistaGateWay;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociateArtistsToAlbumService implements AssociateArtistsToAlbumUseCase{
    private final AlbumGateWay albumGateWay;
    private final ArtistaGateWay artistaGateWay;

    public AssociateArtistsToAlbumService(AlbumGateWay albumGateWay, ArtistaGateWay artistaGateWay) {
        this.albumGateWay = albumGateWay;
        this.artistaGateWay = artistaGateWay;
    }

    @Override
    public Album associate(Integer albumId, List<Integer> artistIds) {
        List<Artista> artistas = artistIds.stream().map(artistaGateWay::findById).toList();
        return albumGateWay.associateArtistas(albumId,artistas);
    }
}
