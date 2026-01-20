package br.com.hamix.infrastructure.controller.Album;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.domain.model.Album;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import br.com.hamix.infrastructure.controller.Album.dto.SaveAlbumRequest;
import br.com.hamix.infrastructure.controller.Album.mapper.SaveAlbumDTOMapper;
import br.com.hamix.usecase.album.list.ListAlbumUseCase;
import br.com.hamix.usecase.album.save.SaveAlbumUseCase;
import br.com.hamix.usecase.album.get.GetAlbumPorIdUseCase;
import br.com.hamix.usecase.album.update.UpdateAlbumUseCase;
import br.com.hamix.usecase.artista.update.UpdateArtistaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("album")
public class AlbumController {

    private final GetAlbumPorIdUseCase getAlbumPorIdUseCase;
    private final SaveAlbumUseCase saveAlbumUseCase;
    private final UpdateAlbumUseCase updateArtistaUseCase;
    private final ListAlbumUseCase listAlbumUseCase;

    public AlbumController(GetAlbumPorIdUseCase getAlbumPorIdUseCase, SaveAlbumUseCase saveAlbumUseCase, UpdateArtistaUseCase updateArtistaUseCase, UpdateAlbumUseCase updateArtistaUseCase1, ListAlbumUseCase listAlbumUseCase) {
        this.getAlbumPorIdUseCase = getAlbumPorIdUseCase;
        this.saveAlbumUseCase = saveAlbumUseCase;
        this.updateArtistaUseCase = updateArtistaUseCase1;
        this.listAlbumUseCase = listAlbumUseCase;
    }

    @Operation(summary = "Buscar album por id",
            description = "Busca um album por id e retorna seus dados caso ele exista",
            tags = "Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Album Encontrado com sucesso"),
            @ApiResponse(responseCode = "404",description = "Album não encontrado"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @GetMapping(value = "{id}")
    public ResponseEntity<Album> getById(@PathVariable String id){
        Optional<Album> opAlbum = getAlbumPorIdUseCase.findAlbumById(Integer.valueOf(id));
        return opAlbum.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Buscar albuns",
            description = "Busca os albuns de maneira paginada e com filtros",
            tags = "Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Album Encontrado com sucesso"),
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


    @Operation(summary = "Atualizar album",
            description = "Atualiza os dados do album e retorna seus dados atualizados",
            tags = "Album")
    @ApiResponse(responseCode = "201", description = "Album atualizado")
    @PutMapping(value = "{id}")
    public ResponseEntity<Album> updateEntity(@PathVariable String id, @Valid @RequestBody SaveAlbumRequest request){
        updateArtistaUseCase.updateAlbum(SaveAlbumDTOMapper.toDomain(request), Integer.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
