package br.com.hamix.usecase.album.getAssociation;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAssociationService implements GetAssociationUseCase {
    private final AlbumGateway albumGateWay;

    public GetAssociationService(AlbumGateway albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public List<Artista> getAssociacaoById(Integer id) {
        return albumGateWay.getAssociacaoById(id);
    }
}
