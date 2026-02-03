package br.com.hamix.usecase.artista.list;

import br.com.hamix.domain.gateway.ArtistaGateway;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListArtistaServiceTest {

	@Test
	void listAlbunsWithPaginationAndFilters_mapsPageToResponse() {
		ArtistaGateway gateway = mock(ArtistaGateway.class);
		ListArtistaService service = new ListArtistaService(gateway);
		PaginationRequest request = PaginationRequest.builder()
				.page(1)
				.size(2)
				.sortBy("id")
				.sortDirection("ASC")
				.build();
		Artista filter = Artista.builder().nome("Rita").build();

		List<Artista> content = List.of(
				Artista.builder().id(1).nome("Rita Lee").build(),
				Artista.builder().id(2).nome("Rita").build()
		);
		Page<Artista> page = new PageImpl<>(content, PageRequest.of(1, 2), 5);
		when(gateway.getPage(eq(request), eq(filter))).thenReturn(page);

		PaginationResponse<Artista> response = service.listAlbunsWithPaginationAndFilters(request, filter);

		assertThat(response.getContent()).hasSize(2);
		assertThat(response.getPage()).isEqualTo("1");
		assertThat(response.getSize()).isEqualTo("2");
		assertThat(response.getTotalPages()).isEqualTo("3");
		assertThat(response.getTotalElements()).isEqualTo("5");
		verify(gateway).getPage(eq(request), eq(filter));
	}
}
