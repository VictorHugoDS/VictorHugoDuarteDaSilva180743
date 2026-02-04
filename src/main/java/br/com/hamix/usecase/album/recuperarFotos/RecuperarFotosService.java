package br.com.hamix.usecase.album.recuperarFotos;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.FotoGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Foto;
import br.com.hamix.infrastructure.storage.FotoStorageGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecuperarFotosService implements RecuperarFotosUseCase{

    private final AlbumGateway albumGateway;
    private final FotoGateway fotoGateway;
    private final FotoStorageGateway fotoStorageGateway;

    public RecuperarFotosService(AlbumGateway albumGateway, FotoGateway fotoGateway, FotoStorageGateway fotoStorageGateway) {
        this.albumGateway = albumGateway;
        this.fotoGateway = fotoGateway;
        this.fotoStorageGateway = fotoStorageGateway;
    }

    @Override
    public List<Foto> recuperarFotosDeAlbum(Integer albumId) {
        Album album = albumGateway.findById(albumId);
        return fotoGateway.findByAlbum(album).stream()
                .map(fotoStorageGateway::recuperarLinkFoto)
                .toList();
    }
}
