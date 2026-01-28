package br.com.hamix.usecase.album.recuperarFotos;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.FotoGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Foto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecuperarFotosService implements RecuperarFotosUseCase{

    private final AlbumGateway albumGateway;
    private final FotoGateway fotoGateway;

    public RecuperarFotosService(AlbumGateway albumGateway, FotoGateway fotoGateway) {
        this.albumGateway = albumGateway;
        this.fotoGateway = fotoGateway;
    }

    @Override
    public List<Foto> recuperarFotosDeAlbum(Integer albumId) {
        Album album = albumGateway.findById(albumId);
        return fotoGateway.findByAlbum(album);
    }
}
