package br.com.hamix.usecase.album.recuperarFotos;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.FotoGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Foto;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecuperarFotosServiceTest {

	@Test
	void recuperarFotosDeAlbum_fetchesAlbumAndFotos() {
		AlbumGateway albumGateway = mock(AlbumGateway.class);
		FotoGateway fotoGateway = mock(FotoGateway.class);
		RecuperarFotosService service = new RecuperarFotosService(albumGateway, fotoGateway);

		Album album = new Album(4, "Meteora", "2003");
		when(albumGateway.findById(4)).thenReturn(album);
		List<Foto> fotos = List.of(Foto.builder().id(1).nome("capa").build());
		when(fotoGateway.findByAlbum(album)).thenReturn(fotos);

		List<Foto> result = service.recuperarFotosDeAlbum(4);

		assertThat(result).isSameAs(fotos);
		verify(fotoGateway).findByAlbum(album);
	}
}
