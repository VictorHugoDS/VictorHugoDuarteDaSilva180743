package br.com.hamix.usecase.artista.save;

import br.com.hamix.domain.gateway.ArtistaGateWay;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

@Service
public class SaveArtistaService implements SaveArtistaUseCase {
    private final ArtistaGateWay artistaGateWay;

    public SaveArtistaService(ArtistaGateWay albumGateWay) {
        this.artistaGateWay = albumGateWay;
    }

    @Override
    public Artista salvarArtista(Artista album) {
        return artistaGateWay.save(album);
    }
}
