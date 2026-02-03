package br.com.hamix.usecase.album.associate;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AssociateArtistsToAlbumServiceTest {

	@Test
	void associate_fetchesArtistsAndDelegatesToGateway() {
		AlbumGateway albumGateway = mock(AlbumGateway.class);
		ArtistaGateway artistaGateway = mock(ArtistaGateway.class);
		AssociateArtistsToAlbumService service = new AssociateArtistsToAlbumService(albumGateway, artistaGateway);
		when(artistaGateway.findById(1)).thenReturn(Artista.builder().id(1).nome("Serj").build());
		when(artistaGateway.findById(2)).thenReturn(Artista.builder().id(2).nome("Mike").build());
		Album album = new Album(5, "Black Blooms", "2010");
		when(albumGateway.associateArtistas(eq(5), any())).thenReturn(album);

		Album result = service.associate(5, List.of(1, 2));

		assertThat(result).isSameAs(album);
		verify(albumGateway).associateArtistas(eq(5), any());
	}
}
