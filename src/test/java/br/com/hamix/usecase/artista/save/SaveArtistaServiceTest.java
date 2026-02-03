package br.com.hamix.usecase.artista.save;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveArtistaServiceTest {

	@Test
	void salvarArtista_delegatesToGateway() {
		ArtistaGateway gateway = mock(ArtistaGateway.class);
		SaveArtistaService service = new SaveArtistaService(gateway);
		Artista artista = Artista.builder().nome("Guns N' Roses").build();
		when(gateway.save(artista)).thenReturn(artista);

		Artista result = service.salvarArtista(artista);

		assertThat(result).isSameAs(artista);
		verify(gateway).save(artista);
	}
}
