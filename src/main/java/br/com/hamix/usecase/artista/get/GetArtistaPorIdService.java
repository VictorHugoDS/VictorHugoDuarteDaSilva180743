package br.com.hamix.usecase.artista.get;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

@Service
public class GetArtistaPorIdService implements GetArtistaPorIdUseCase {
    private final ArtistaGateway artistaGateWay;

    public GetArtistaPorIdService(ArtistaGateway artistaGateWay) {
        this.artistaGateWay = artistaGateWay;
    }

    @Override
    public Artista findArtistaById(Integer id) {
        return artistaGateWay.findById(id);
    }
}
