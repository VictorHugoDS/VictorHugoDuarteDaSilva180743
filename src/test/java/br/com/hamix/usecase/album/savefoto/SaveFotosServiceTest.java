package br.com.hamix.usecase.album.savefoto;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.gateway.FotoGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Foto;
import br.com.hamix.infrastructure.storage.FotoStorageGateway;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveFotosServiceTest {

	@Test
	void salvarFotosAlbum_uploadsAndSavesEachFoto() {
		AlbumGateway albumGateway = mock(AlbumGateway.class);
		FotoStorageGateway fotoStorageGateway = mock(FotoStorageGateway.class);
		FotoGateway fotoGateway = mock(FotoGateway.class);
		SaveFotosService service = new SaveFotosService(albumGateway, fotoStorageGateway, fotoGateway);

		Album album = new Album(1, "Hybrid Theory", "2000");
		when(albumGateway.findById(1)).thenReturn(album);

		MultipartFile file1 = mock(MultipartFile.class);
		MultipartFile file2 = mock(MultipartFile.class);
		Foto foto1 = Foto.builder().id(1).nome("capa").url("http://img/1").build();
		Foto foto2 = Foto.builder().id(2).nome("verso").url("http://img/2").build();
		when(fotoStorageGateway.uploadFoto(file1)).thenReturn(foto1);
		when(fotoStorageGateway.uploadFoto(file2)).thenReturn(foto2);

		service.salvarFotosAlbum(1, List.of(file1, file2));

		verify(fotoGateway).save(foto1);
		verify(fotoGateway).save(foto2);
	}
}
