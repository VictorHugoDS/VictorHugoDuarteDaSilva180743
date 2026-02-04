package br.com.hamix.infrastructure.controller.Album;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.model.Foto;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import br.com.hamix.infrastructure.controller.Album.dto.AssociationResponse;
import br.com.hamix.infrastructure.controller.Album.dto.FotoResponse;
import br.com.hamix.infrastructure.controller.Album.dto.SaveAlbumRequest;
import br.com.hamix.usecase.album.associate.AssociateArtistsToAlbumUseCase;
import br.com.hamix.usecase.album.get.GetAlbumUseCase;
import br.com.hamix.usecase.album.getAssociation.GetAssociationUseCase;
import br.com.hamix.usecase.album.list.ListAlbumUseCase;
import br.com.hamix.usecase.album.recuperarFotos.RecuperarFotosUseCase;
import br.com.hamix.usecase.album.save.SaveAlbumUseCase;
import br.com.hamix.usecase.album.savefoto.SaveFotosUseCase;
import br.com.hamix.usecase.album.update.UpdateAlbumUseCase;
import br.com.hamix.usecase.artista.update.UpdateArtistaUseCase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumControllerTest {

	@Mock
	private GetAlbumUseCase getAlbumUseCase;

	@Mock
	private SaveAlbumUseCase saveAlbumUseCase;

	@Mock
	private UpdateAlbumUseCase updateAlbumUseCase;

	@Mock
	private ListAlbumUseCase listAlbumUseCase;

	@Mock
	private AssociateArtistsToAlbumUseCase associateArtistsToAlbumUseCase;

	@Mock
	private GetAssociationUseCase getAssociationUseCase;

	@Mock
	private SaveFotosUseCase saveFotosUseCase;

	@Mock
	private RecuperarFotosUseCase recuperarFotosUseCase;

	@Mock
	private UpdateArtistaUseCase updateArtistaUseCase;

	@InjectMocks
	private AlbumController albumController;

	@Test
	void getById_returnsAlbum() {
		Album album = new Album(1, "Hybrid Theory", "2000");
		when(getAlbumUseCase.findAlbumById(1)).thenReturn(album);

		ResponseEntity<Album> response = albumController.getById("1");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1);
		assertThat(response.getBody().getNome()).isEqualTo("Hybrid Theory");
		assertThat(response.getBody().getAno()).isEqualTo("2000");
	}

	@Test
	void list_returnsPagination() {
		Album album = new Album(10, "Meteora", "2003");
		PaginationResponse<Album> response = new PaginationResponse<>(
				List.of(album),
				"0",
				"10",
				"1",
				"1"
		);
		when(listAlbumUseCase.listAlbunsWithPaginationAndFilters(any(), any())).thenReturn(response);

		ResponseEntity<PaginationResponse<Album>> result = albumController.getById(
				"0",
				"10",
				"id",
				"ASC",
				"Meteora"
		);

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).hasSize(1);
		assertThat(result.getBody().getContent().get(0).getId()).isEqualTo(10);

		ArgumentCaptor<PaginationRequest> requestCaptor = ArgumentCaptor.forClass(PaginationRequest.class);
		ArgumentCaptor<Album> filterCaptor = ArgumentCaptor.forClass(Album.class);
		verify(listAlbumUseCase).listAlbunsWithPaginationAndFilters(requestCaptor.capture(), filterCaptor.capture());
		assertThat(requestCaptor.getValue().getPage()).isEqualTo(0);
		assertThat(requestCaptor.getValue().getSize()).isEqualTo(10);
		assertThat(filterCaptor.getValue().getNome()).isEqualTo("Meteora");
	}

	@Test
	void list_withInvalidPage_throwsConversionException() {
		assertThatThrownBy(() -> albumController.getById("abc", "10", "id", "ASC", null))
				.isInstanceOf(ConversionException.class)
				.hasMessageContaining("Valor de inteiro inválido");
	}

	@Test
	void save_returnsCreated() {
		Album saved = new Album(2, "Minutes to Midnight", "2007");
		when(saveAlbumUseCase.salvarAlbum(any())).thenReturn(saved);

		SaveAlbumRequest request = new SaveAlbumRequest("Minutes to Midnight", "2007");

		ResponseEntity<Album> response = albumController.saveEntity(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(2);
	}

	@Test
	void update_returnsNoContent() {
		SaveAlbumRequest request = new SaveAlbumRequest("One More Light", "2017");

		ResponseEntity<Album> response = albumController.updateEntity("5", request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		ArgumentCaptor<Album> captor = ArgumentCaptor.forClass(Album.class);
		verify(updateAlbumUseCase).updateAlbum(captor.capture(), eq(5));
		assertThat(captor.getValue().getNome()).isEqualTo("One More Light");
		assertThat(captor.getValue().getAno()).isEqualTo("2017");
	}

	@Test
	void associateArtists_returnsAssociationResponse() {
		Album album = new Album(3, "Black Blooms", "2010");
		when(associateArtistsToAlbumUseCase.associate(eq(3), eq(List.of(1, 2)))).thenReturn(album);
		when(getAssociationUseCase.getAssociacaoById(3)).thenReturn(List.of(
				Artista.builder().id(1).nome("Serj").origem("Armenia").build(),
				Artista.builder().id(2).nome("Mike").origem("Estados Unidos").build()
		));

		ResponseEntity<AssociationResponse> response = albumController.associateArtists(3, List.of(1, 2));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getNome()).isEqualTo("Black Blooms");
		assertThat(response.getBody().getArtistas()).hasSize(2);
	}

	@Test
	void getAssociacoes_returnsAssociationResponse() {
		Album album = new Album(7, "Harakiri", "2012");
		when(getAlbumUseCase.findAlbumById(7)).thenReturn(album);
		when(getAssociationUseCase.getAssociacaoById(7)).thenReturn(List.of(
				Artista.builder().id(3).nome("Serj").origem("Armenia").build()
		));

		ResponseEntity<AssociationResponse> response = albumController.getAssociacoes(7);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getNome()).isEqualTo("Harakiri");
		assertThat(response.getBody().getArtistas()).hasSize(1);
	}

	@Test
	void saveFotosAlbum_callsUseCase() {
		MultipartFile file = mock(MultipartFile.class);

		ResponseEntity<Void> response = albumController.saveFotosAlbum(9, List.of(file));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		verify(saveFotosUseCase).salvarFotosAlbum(9, List.of(file));
	}

	@Test
	void getFotosAlbum_returnsFotoResponses() {
		when(recuperarFotosUseCase.recuperarFotosDeAlbum(4)).thenReturn(List.of(
				Foto.builder().id(1).nome("capa").url("http://img/1").build(),
				Foto.builder().id(2).nome("verso").url("http://img/2").build()
		));

		ResponseEntity<Object> response = albumController.saveFotosAlbum(4);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		//assertThat(response.getBody()).hasSize(2);
	}
}
