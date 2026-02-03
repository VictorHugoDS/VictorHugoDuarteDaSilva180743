package br.com.hamix.usecase.artista.update;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateArtistaServiceTest {

	@Test
	void updateArtista_setsIdAndSaves() {
		ArtistaGateway gateway = mock(ArtistaGateway.class);
		UpdateArtistaService service = new UpdateArtistaService(gateway);
		Artista reference = Artista.builder().nome("Serj Tankian").build();

		service.updateArtista(reference, 7);

		ArgumentCaptor<Artista> captor = ArgumentCaptor.forClass(Artista.class);
		verify(gateway).save(captor.capture());
		assertThat(captor.getValue().getId()).isEqualTo(7);
		assertThat(captor.getValue().getNome()).isEqualTo("Serj Tankian");
	}
}
