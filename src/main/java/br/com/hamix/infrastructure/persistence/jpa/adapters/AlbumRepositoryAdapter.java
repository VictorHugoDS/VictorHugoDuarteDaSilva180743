package br.com.hamix.infrastructure.persistence.jpa.adapters;

import br.com.hamix.config.exception.custom.DataNotFoundedException;
import br.com.hamix.config.exception.custom.DatabaseException;
import br.com.hamix.domain.gateway.AlbumGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.AlbumEntityMapper;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.ArtistaEntityMapper;
import br.com.hamix.infrastructure.persistence.jpa.adapters.mappers.PaginationMapper;
import br.com.hamix.infrastructure.persistence.jpa.AlbumEntity;
import br.com.hamix.infrastructure.persistence.jpa.AlbumRepository;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaEntity;
import br.com.hamix.infrastructure.persistence.jpa.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumRepositoryAdapter implements AlbumGateway {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistaRepository artistaRepository;


    @Override
    public Album save(Album album) {
        AlbumEntity entity = AlbumEntityMapper.toEntity(album);
        try{
            AlbumEntity savedEntity = albumRepository.save(entity);
            return AlbumEntityMapper.toDomain(savedEntity);
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao salvar a entidade",e);
        }

    }

    @Override
    public Album findById(Integer id) {
        AlbumEntity entity =  albumRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundedException("Não foi possível encontrar o álbum"));
        return AlbumEntityMapper.toDomain(entity);

    }

    @Override
    public Page<Album> getPage(PaginationRequest pagination, Album toFilter) {
        Pageable pageable = PaginationMapper.toPageable(pagination);
        AlbumEntity toFilterEntity = AlbumEntityMapper.toEntity(toFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "ano")
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<AlbumEntity> example = Example.of(toFilterEntity, matcher);

        try {
            return albumRepository.findAll(example,pageable).map(AlbumEntityMapper::toDomain);
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao recuperar as entidades",e);
        }

    }


    @Override
    public Album associateArtistas(Integer idAlbum, List<Artista> artistasList) {
        AlbumEntity entity = albumRepository.findById(idAlbum)
                .orElseThrow(() -> new DataNotFoundedException("Não foi possível encontrar o álbum"));
        try {
            List<Integer> artistIds = artistasList.stream()
                    .map(Artista::getId)
                    .toList();
            List<ArtistaEntity> artistaEntityList = artistaRepository.findAllById(artistIds);
            for (ArtistaEntity artistaEntity : artistaEntityList) {
                List<AlbumEntity> albuns = artistaEntity.getAlbuns();
                if (albuns == null) {
                    albuns = new ArrayList<>();
                }
                boolean albumJaLinkado = albuns.stream()
                        .anyMatch(album -> album.getId().equals(entity.getId()));
                if (!albumJaLinkado) {
                    albuns.add(entity);
                }
                artistaEntity.setAlbuns(albuns);
            }
            artistaRepository.saveAll(artistaEntityList);
            entity.setArtistas(artistaEntityList);
            return AlbumEntityMapper.toDomain(entity);
        } catch (DatabaseException e) {
            throw new RuntimeException("Ocorreu um erro ao salvar a entidade",e);
        }
    }

    @Override
    public List<Artista> getAssociacaoById(Integer id) {
        AlbumEntity entity = albumRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundedException("Não foi possível encontrar o álbum"));

        return entity.getArtistas().stream().map(ArtistaEntityMapper::toDomain).toList();
    }

}
