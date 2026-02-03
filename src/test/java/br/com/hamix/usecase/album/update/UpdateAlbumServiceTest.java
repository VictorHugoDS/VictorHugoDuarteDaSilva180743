package br.com.hamix.usecase.album.update;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateAlbumServiceTest {

	@Test
	void updateAlbum_setsIdAndSaves() {
		AlbumGateway gateway = mock(AlbumGateway.class);
		UpdateAlbumService service = new UpdateAlbumService(gateway);
		Album reference = new Album(null, "Minutes to Midnight", "2007");

		service.updateAlbum(reference, 7);

		ArgumentCaptor<Album> captor = ArgumentCaptor.forClass(Album.class);
		verify(gateway).save(captor.capture());
		assertThat(captor.getValue().getId()).isEqualTo(7);
		assertThat(captor.getValue().getNome()).isEqualTo("Minutes to Midnight");
	}
}
