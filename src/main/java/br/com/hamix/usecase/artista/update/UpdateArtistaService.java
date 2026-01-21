package br.com.hamix.usecase.artista.update;

import br.com.hamix.domain.gateway.ArtistaGateWay;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

@Service
public class UpdateArtistaService implements UpdateArtistaUseCase{
    private final ArtistaGateWay artistaGateWay;

    public UpdateArtistaService(ArtistaGateWay artistaGateWay) {
        this.artistaGateWay = artistaGateWay;
    }

    @Override
    public void updateArtista(Artista reference, Integer id) {
        reference.setId(id);
        artistaGateWay.save(reference);
    }
}
