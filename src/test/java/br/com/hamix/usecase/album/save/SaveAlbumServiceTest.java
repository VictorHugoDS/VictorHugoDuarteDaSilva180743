package br.com.hamix.usecase.album.save;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveAlbumServiceTest {

	@Test
	void salvarAlbum_delegatesToGateway() {
		AlbumGateway gateway = mock(AlbumGateway.class);
		SaveAlbumService service = new SaveAlbumService(gateway);
		Album album = new Album(null, "Hybrid Theory", "2000");
		when(gateway.save(album)).thenReturn(album);

		Album result = service.salvarAlbum(album);

		assertThat(result).isSameAs(album);
		verify(gateway).save(album);
	}
}
