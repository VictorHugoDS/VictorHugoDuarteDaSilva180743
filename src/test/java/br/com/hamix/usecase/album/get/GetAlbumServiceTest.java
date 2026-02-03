package br.com.hamix.usecase.album.get;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAlbumServiceTest {

	@Test
	void findAlbumById_delegatesToGateway() {
		AlbumGateway gateway = mock(AlbumGateway.class);
		GetAlbumService service = new GetAlbumService(gateway);
		Album album = new Album(3, "Meteora", "2003");
		when(gateway.findById(3)).thenReturn(album);

		Album result = service.findAlbumById(3);

		assertThat(result).isSameAs(album);
		ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(gateway).findById(idCaptor.capture());
		assertThat(idCaptor.getValue()).isEqualTo(3);
	}
}
