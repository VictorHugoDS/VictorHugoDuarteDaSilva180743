package br.com.hamix.infrastructure.gateways;


import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;

public class ArtistaEntityMapper {
    public static ArtistaEntity toEntity(Artista domain){
        return ArtistaEntity.builder()
                .id(domain.id())
                .nome(domain.nome())
                .origem(domain.origem())
                .build();
    }
    public static Artista toDomain(ArtistaEntity entity){
        return new Artista(entity.getId(),entity.getNome(),entity.getOrigem());
    }
}
