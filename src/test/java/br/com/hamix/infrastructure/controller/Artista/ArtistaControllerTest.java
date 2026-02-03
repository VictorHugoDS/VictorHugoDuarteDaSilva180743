package br.com.hamix.infrastructure.controller.Artista;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import br.com.hamix.usecase.artista.get.GetArtistaPorIdUseCase;
import br.com.hamix.usecase.artista.list.ListArtistaUseCase;
import br.com.hamix.usecase.artista.save.SaveArtistaUseCase;
import br.com.hamix.usecase.artista.update.UpdateArtistaUseCase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistaControllerTest {

	@Mock
	private GetArtistaPorIdUseCase getArtistaPorIdUseCase;

	@Mock
	private SaveArtistaUseCase saveArtistaUseCase;

	@Mock
	private UpdateArtistaUseCase updateArtistaUseCase;

	@Mock
	private ListArtistaUseCase listArtistaUseCase;

	@InjectMocks
	private ArtistaController artistaController;

	@Test
	void getById_returnsArtista() {
		Artista artista = Artista.builder()
				.id(1)
				.nome("Serj Tankian")
				.origem("Armenia")
				.build();
		when(getArtistaPorIdUseCase.findArtistaById(1)).thenReturn(artista);

		ResponseEntity<Artista> response = artistaController.getById("1");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1);
		assertThat(response.getBody().getNome()).isEqualTo("Serj Tankian");
		assertThat(response.getBody().getOrigem()).isEqualTo("Armenia");
	}

	@Test
	void list_returnsPagination() {
		Artista artista = Artista.builder().id(10).nome("Rita Lee").origem("Brasil").build();
		PaginationResponse<Artista> response = new PaginationResponse<>(
				List.of(artista),
				"0",
				"10",
				"1",
				"1"
		);
		when(listArtistaUseCase.listAlbunsWithPaginationAndFilters(any(), any())).thenReturn(response);

		ResponseEntity<PaginationResponse<Artista>> result = artistaController.getById(
				"0",
				"10",
				"id",
				"ASC",
				null
		);

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).hasSize(1);
		assertThat(result.getBody().getContent().get(0).getId()).isEqualTo(10);

		ArgumentCaptor<PaginationRequest> requestCaptor = ArgumentCaptor.forClass(PaginationRequest.class);
		ArgumentCaptor<Artista> filterCaptor = ArgumentCaptor.forClass(Artista.class);
		verify(listArtistaUseCase).listAlbunsWithPaginationAndFilters(requestCaptor.capture(), filterCaptor.capture());

		assertThat(requestCaptor.getValue().getPage()).isEqualTo(0);
		assertThat(requestCaptor.getValue().getSize()).isEqualTo(10);
		assertThat(requestCaptor.getValue().getSortBy()).isEqualTo("id");
		assertThat(requestCaptor.getValue().getSortDirection()).isEqualTo("ASC");
		assertThat(filterCaptor.getValue().getNome()).isNull();
	}

	@Test
	void list_withInvalidPage_throwsConversionException() {
		assertThatThrownBy(() -> artistaController.getById("abc", "10", "id", "ASC", null))
				.isInstanceOf(ConversionException.class)
				.hasMessageContaining("Valor de inteiro inválido");
	}

	@Test
	void save_returnsCreated() {
		Artista saved = Artista.builder()
				.id(2)
				.nome("Mike Shinoda")
				.origem("Estados Unidos")
				.albuns(List.of(Album.builder().id(1).build()))
				.build();
		when(saveArtistaUseCase.salvarArtista(any())).thenReturn(saved);

		br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest request =
				new br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest(
						"Mike Shinoda",
						"Estados Unidos",
						List.of(1)
				);

		ResponseEntity<Artista> response = artistaController.saveEntity(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(2);
		assertThat(response.getBody().getNome()).isEqualTo("Mike Shinoda");
		assertThat(response.getBody().getOrigem()).isEqualTo("Estados Unidos");
	}

	@Test
	void update_returnsNoContent() {
		br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest request =
				new br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest(
						"Rita Lee",
						"Brasil",
						List.of(2, 3)
				);

		ResponseEntity<Artista> response = artistaController.updateEntity("5", request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ArgumentCaptor<Artista> artistaCaptor = ArgumentCaptor.forClass(Artista.class);
		verify(updateArtistaUseCase).updateArtista(artistaCaptor.capture(), eq(5));
		assertThat(artistaCaptor.getValue().getNome()).isEqualTo("Rita Lee");
		assertThat(artistaCaptor.getValue().getOrigem()).isEqualTo("Brasil");
	}
}
