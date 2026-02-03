package br.com.hamix.usecase.album.getAssociation;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Artista;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAssociationServiceTest {

	@Test
	void getAssociacaoById_delegatesToGateway() {
		AlbumGateway gateway = mock(AlbumGateway.class);
		GetAssociationService service = new GetAssociationService(gateway);
		List<Artista> artistas = List.of(Artista.builder().id(1).nome("Serj").build());
		when(gateway.getAssociacaoById(9)).thenReturn(artistas);

		List<Artista> result = service.getAssociacaoById(9);

		assertThat(result).isSameAs(artistas);
		verify(gateway).getAssociacaoById(9);
	}
}
