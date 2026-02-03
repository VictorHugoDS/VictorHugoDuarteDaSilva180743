package br.com.hamix.usecase.artista.get;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetArtistaPorIdServiceTest {

	@Test
	void findArtistaById_delegatesToGateway() {
		ArtistaGateway gateway = mock(ArtistaGateway.class);
		GetArtistaPorIdService service = new GetArtistaPorIdService(gateway);
		Artista artista = Artista.builder().id(3).nome("Amy Winehouse").build();
		when(gateway.findById(3)).thenReturn(artista);

		Artista result = service.findArtistaById(3);

		assertThat(result).isSameAs(artista);
		ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(gateway).findById(idCaptor.capture());
		assertThat(idCaptor.getValue()).isEqualTo(3);
	}
}
