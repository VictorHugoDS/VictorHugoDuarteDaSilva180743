package br.com.hamix.usecase.artista.get;

import br.com.hamix.domain.gateway.ArtistaGateWay;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.gateways.mappers.ArtistaEntityMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetArtistaPorIdService implements GetArtistaPorIdUseCase {
    private final ArtistaGateWay artistaGateWay;

    public GetArtistaPorIdService(ArtistaGateWay artistaGateWay) {
        this.artistaGateWay = artistaGateWay;
    }

    @Override
    public Artista findArtistaById(Integer id) {
        return artistaGateWay.findById(id);
    }
}
