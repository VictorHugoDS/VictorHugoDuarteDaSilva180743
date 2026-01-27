package br.com.hamix.usecase.artista.save;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

@Service
public class SaveArtistaService implements SaveArtistaUseCase {
    private final ArtistaGateway artistaGateWay;

    public SaveArtistaService(ArtistaGateway albumGateWay) {
        this.artistaGateWay = albumGateWay;
    }

    @Override
    public Artista salvarArtista(Artista album) {
        return artistaGateWay.save(album);
    }
}
