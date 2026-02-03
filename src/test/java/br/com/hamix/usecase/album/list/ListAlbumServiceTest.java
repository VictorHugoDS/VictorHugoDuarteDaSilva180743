package br.com.hamix.usecase.album.list;

import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
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

class ListAlbumServiceTest {

	@Test
	void listAlbunsWithPaginationAndFilters_mapsPageToResponse() {
		AlbumGateway gateway = mock(AlbumGateway.class);
		ListAlbumService service = new ListAlbumService(gateway);
		PaginationRequest request = PaginationRequest.builder()
				.page(1)
				.size(2)
				.sortBy("id")
				.sortDirection("ASC")
				.build();
		Album filter = new Album(null, "Rita", null);

		List<Album> content = List.of(
				new Album(1, "Rita Lee", "1987"),
				new Album(2, "Rita", "2001")
		);
		Page<Album> page = new PageImpl<>(content, PageRequest.of(1, 2), 5);
		when(gateway.getPage(eq(request), eq(filter))).thenReturn(page);

		PaginationResponse<Album> response = service.listAlbunsWithPaginationAndFilters(request, filter);

		assertThat(response.getContent()).hasSize(2);
		assertThat(response.getPage()).isEqualTo("1");
		assertThat(response.getSize()).isEqualTo("2");
		assertThat(response.getTotalPages()).isEqualTo("3");
		assertThat(response.getTotalElements()).isEqualTo("5");
		verify(gateway).getPage(eq(request), eq(filter));
	}
}
