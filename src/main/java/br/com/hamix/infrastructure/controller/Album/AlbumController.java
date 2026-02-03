package br.com.hamix.infrastructure.controller.Album;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import br.com.hamix.infrastructure.controller.Album.dto.ArtistaDTO;
import br.com.hamix.infrastructure.controller.Album.dto.AssociationResponse;
import br.com.hamix.infrastructure.controller.Album.dto.FotoResponse;
import br.com.hamix.infrastructure.controller.Album.dto.SaveAlbumRequest;
import br.com.hamix.infrastructure.controller.Album.mapper.ArtistaDTOMapper;
import br.com.hamix.infrastructure.controller.Album.mapper.SaveAlbumDTOMapper;
import br.com.hamix.usecase.album.associate.AssociateArtistsToAlbumUseCase;
import br.com.hamix.usecase.album.getAssociation.GetAssociationUseCase;
import br.com.hamix.usecase.album.list.ListAlbumUseCase;
import br.com.hamix.usecase.album.recuperarFotos.RecuperarFotosUseCase;
import br.com.hamix.usecase.album.save.SaveAlbumUseCase;
import br.com.hamix.usecase.album.get.GetAlbumUseCase;
import br.com.hamix.usecase.album.savefoto.SaveFotosUseCase;
import br.com.hamix.usecase.album.update.UpdateAlbumUseCase;
import br.com.hamix.usecase.artista.update.UpdateArtistaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/album")
@Tag(name = "Album", description = "Endpoints de cadastro e consulta de albuns")
public class AlbumController {

    private final GetAlbumUseCase getAlbumUseCase;
    private final SaveAlbumUseCase saveAlbumUseCase;
    private final UpdateAlbumUseCase updateArtistaUseCase;
    private final ListAlbumUseCase listAlbumUseCase;
    private final AssociateArtistsToAlbumUseCase associateArtistsToAlbumUseCase;
    private final GetAssociationUseCase getAssociationUseCase;
    private final SaveFotosUseCase saveFotosUseCase;
    private final RecuperarFotosUseCase recuperarFotosUseCase;

    public AlbumController(GetAlbumUseCase getAlbumUseCase, SaveAlbumUseCase saveAlbumUseCase, UpdateArtistaUseCase updateArtistaUseCase, UpdateAlbumUseCase updateArtistaUseCase1, ListAlbumUseCase listAlbumUseCase, AssociateArtistsToAlbumUseCase associateArtistsToAlbumUseCase, GetAssociationUseCase getAssociationUseCase, SaveFotosUseCase saveFotosUseCase, RecuperarFotosUseCase recuperarFotosUseCase) {
        this.getAlbumUseCase = getAlbumUseCase;
        this.saveAlbumUseCase = saveAlbumUseCase;
        this.updateArtistaUseCase = updateArtistaUseCase1;
        this.listAlbumUseCase = listAlbumUseCase;
        this.associateArtistsToAlbumUseCase = associateArtistsToAlbumUseCase;
        this.getAssociationUseCase = getAssociationUseCase;
        this.saveFotosUseCase = saveFotosUseCase;
        this.recuperarFotosUseCase = recuperarFotosUseCase;
    }

    @Operation(summary = "Buscar album por id",
            description = "Busca um album por id e retorna seus dados caso ele exista",
            tags = "Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Album Encontrado com sucesso"),
            @ApiResponse(responseCode = "404",description = "Album não encontrado"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Album> getById(@PathVariable String id){
        Album Album = getAlbumUseCase.findAlbumById(Integer.valueOf(id));
        return ResponseEntity.ok(Album);

    }

    @Operation(summary = "Buscar albuns",
            description = "Busca os albuns de maneira paginada e com filtros",
            tags = "Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Album Encontrado com sucesso"),
            @ApiResponse(responseCode = "400",description = "Erro ao ler os dados de entrada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @GetMapping(value = "/list")
    public ResponseEntity<PaginationResponse<Album>> getById(
            @RequestParam(required = false,defaultValue = "0") String page,
            @RequestParam(required = false,defaultValue = "10") String size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String name
    ){
        try{
            PaginationRequest request = PaginationRequest.builder()
                    .page(Integer.valueOf(page))
                    .size(Integer.valueOf(size))
                    .sortBy(sortBy)
                    .sortDirection(sortDir)
                    .build();
            Album albumFilter = new Album(0,name,"");
            PaginationResponse<Album> response = listAlbumUseCase.listAlbunsWithPaginationAndFilters(request,albumFilter);
            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            throw new ConversionException("Valor de inteiro inválido", e);
        }
    }




    @Operation(summary = "Salvar album",
            description = "Salva os dados do album e retorna seus dados criados",
            tags = "Album")
    @ApiResponse(responseCode = "201", description = "Album criado")
    @PostMapping
    public ResponseEntity<Album> saveEntity(@Valid @RequestBody SaveAlbumRequest request){
        Album albumSalvo = saveAlbumUseCase.salvarAlbum(SaveAlbumDTOMapper.toDomain(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(albumSalvo);

    }


    @Operation(summary = "Associar álbum a artistas",
            description = "Associa um álbum a seus artista",
            tags = "Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Album associado com sucesso"),
            @ApiResponse(responseCode = "400",description = "Erro ao ler os dados de entrada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar a entidade com os dados passados"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @PostMapping(value = "/{idAlbum}/associarArtistas")
    public ResponseEntity<AssociationResponse> associateArtists(
            @RequestParam Integer idAlbum,
            @Valid @RequestBody List<Integer> idsArtistas){
        Album album = associateArtistsToAlbumUseCase.associate(idAlbum,idsArtistas);
        List<ArtistaDTO> artistasAssociados = getAssociationUseCase.getAssociacaoById(idAlbum)
                .stream().map(ArtistaDTOMapper::toDto).toList();
        AssociationResponse response = AssociationResponse.builder()
                .nome(album.getNome())
                .ano(album.getAno())
                .artistas(artistasAssociados)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar Associação de álbum com artistas",
            description = "Recupera uma lista de associação dos artistas de um determinado álbum",
            tags = "Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Album Encontrado com sucesso"),
            @ApiResponse(responseCode = "400",description = "Erro ao ler os dados de entrada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar a entidade com os dados passados"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @GetMapping(value = "/{id}/associacoes")
    public ResponseEntity<AssociationResponse> getAssociacoes(@PathVariable Integer id){
        Album album = getAlbumUseCase.findAlbumById(id);
        List<ArtistaDTO> artistasAssociados = getAssociationUseCase.getAssociacaoById(id)
                .stream().map(ArtistaDTOMapper::toDto).toList();
        AssociationResponse response = AssociationResponse.builder()
                .nome(album.getNome())
                .ano(album.getAno())
                .artistas(artistasAssociados)
                .build();
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Atualizar album",
            description = "Atualiza os dados do album e retorna seus dados atualizados",
            tags = "Album")
    @ApiResponse(responseCode = "201", description = "Album atualizado")
    @PutMapping(value = "{id}")
    public ResponseEntity<Album> updateEntity(@PathVariable String id, @Valid @RequestBody SaveAlbumRequest request){
        updateArtistaUseCase.updateAlbum(SaveAlbumDTOMapper.toDomain(request), Integer.valueOf(id));
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Salvar fotos de um album",
            description = "Salva as fotos de um album",
            tags = "Album")
    @ApiResponse(responseCode = "201", description = "Fotos salvas")
    @PostMapping(value = "/{idAlbum}/fotos", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> saveFotosAlbum(
            @PathVariable Integer idAlbum,
            @RequestPart("fotos") List<MultipartFile> fotos) {
        saveFotosUseCase.salvarFotosAlbum(idAlbum, fotos);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Recuperar url das fotos de um album",
            description = "Recupera uma lista de urls das fotos do respectivo album",
            tags = "Album")
    @ApiResponse(responseCode = "200", description = "Url das fotos recuperadas com sucesso")
    @GetMapping(value = "/{idAlbum}/fotos", produces = {"multipart/form-data"})
    public ResponseEntity<List<FotoResponse>> saveFotosAlbum(@PathVariable Integer idAlbum){
        List<FotoResponse> response = recuperarFotosUseCase.recuperarFotosDeAlbum(idAlbum)
                .stream().map(foto -> FotoResponse
                        .builder()
                        .id(foto.getId())
                        .nome(foto.getNome())
                        .url(foto.getUrl())
                        .build())
                .toList();
        return ResponseEntity.ok(response);
    }

}
