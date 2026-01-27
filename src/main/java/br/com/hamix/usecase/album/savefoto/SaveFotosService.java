package br.com.hamix.usecase.album.savefoto;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.FotoGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Foto;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;
import br.com.hamix.infrastructure.storage.FotoStorageGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SaveFotosService implements SaveFotosUseCase{

    private final AlbumGateway albumGateway;
    private final FotoStorageGateway fotoStorageGateway;
    private final FotoGateway fotoGateway;

    public SaveFotosService(AlbumGateway albumGateway, FotoStorageGateway fotoStorageGateway, FotoGateway fotoGateway) {
        this.albumGateway = albumGateway;
        this.fotoStorageGateway = fotoStorageGateway;
        this.fotoGateway = fotoGateway;
    }

    @Override
    public void salvarFotosAlbum(Integer idAlbum, List<MultipartFile> multipartFileList) {
        Album album = albumGateway.findById(idAlbum);
        for (MultipartFile file : multipartFileList) {
            Foto foto = fotoStorageGateway.uploadFoto(file);
            foto.setAlbum(album);
            fotoGateway.save(foto);
        }
    }
}
