package br.com.hamix.infrastructure.controller.Artista.mapper;

import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SaveArtistaDTOMapperTest {

	@Test
	void toDomain_mapsIdsAlbumToAlbuns() {
		SaveArtistaRequest request = new SaveArtistaRequest("Rita Lee", "Brasil", List.of(1, 2));

		Artista artista = SaveArtistaDTOMapper.toDomain(request);

		assertThat(artista.getNome()).isEqualTo("Rita Lee");
		assertThat(artista.getOrigem()).isEqualTo("Brasil");
		assertThat(artista.getAlbuns()).hasSize(2);
		assertThat(artista.getAlbuns().get(0).getId()).isEqualTo(1);
		assertThat(artista.getAlbuns().get(1).getId()).isEqualTo(2);
	}

	@Test
	void toDomain_withNullIdsAlbum_createsEmptyList() {
		SaveArtistaRequest request = new SaveArtistaRequest("Serj Tankian", "Armenia", null);

		Artista artista = SaveArtistaDTOMapper.toDomain(request);

		assertThat(artista.getAlbuns()).isEmpty();
	}
}
