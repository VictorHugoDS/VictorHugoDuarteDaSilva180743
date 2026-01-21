package br.com.hamix.usecase.album.getAssociation;

import br.com.hamix.domain.gateway.AlbumGateWay;
import br.com.hamix.domain.model.Artista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAssociationService implements GetAssociationUseCase {
    private final AlbumGateWay albumGateWay;

    public GetAssociationService(AlbumGateWay albumGateWay) {
        this.albumGateWay = albumGateWay;
    }

    @Override
    public List<Artista> getAssociacaoById(Integer id) {
        return albumGateWay.getAssociacaoById(id);
    }
}
