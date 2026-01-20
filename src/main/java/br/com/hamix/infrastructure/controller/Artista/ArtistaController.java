package br.com.hamix.infrastructure.controller.Artista;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.model.Artista;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest;
import br.com.hamix.infrastructure.controller.Artista.mapper.SaveArtistaDTOMapper;
import br.com.hamix.usecase.artista.list.ListArtistaUseCase;
import br.com.hamix.usecase.artista.save.SaveArtistaUseCase;
import br.com.hamix.usecase.artista.get.GetArtistaPorIdUseCase;
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
@RequestMapping("artista")
public class ArtistaController {

    private final GetArtistaPorIdUseCase getArtistaPorIdUseCase;
    private final SaveArtistaUseCase saveArtistaUseCase;
    private final UpdateArtistaUseCase updateArtistaUseCase;
    private final ListArtistaUseCase listArtistaUseCase;

    public ArtistaController(GetArtistaPorIdUseCase getArtistaPorIdUseCase, SaveArtistaUseCase saveArtistaUseCase, UpdateArtistaUseCase updateArtistaUseCase, ListArtistaUseCase listArtistaUseCase) {
        this.getArtistaPorIdUseCase = getArtistaPorIdUseCase;
        this.saveArtistaUseCase = saveArtistaUseCase;
        this.updateArtistaUseCase = updateArtistaUseCase;
        this.listArtistaUseCase = listArtistaUseCase;
    }

    @Operation(summary = "Buscar artista por id",
            description = "Busca um artista por id e retorna seus dados caso ele exista",
            tags = "Artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Artista Encontrado com sucesso"),
            @ApiResponse(responseCode = "404",description = "Artista não encontrado"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @GetMapping(value = "{id}")
    public ResponseEntity<Artista> getById(@PathVariable String id){
        Optional<Artista> opArtista = getArtistaPorIdUseCase.findArtistaById(Integer.valueOf(id));
        return opArtista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Buscar artistas",
            description = "Busca os artistas de maneira paginada e com filtros",
            tags = "Artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Artistas Encontrados com sucesso"),
            @ApiResponse(responseCode = "400",description = "Erro ao ler os dados de entrada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @GetMapping(value = "/list")
    public ResponseEntity<PaginationResponse<Artista>> getById(
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
            Artista artistaFilter = new Artista(0,name,"");
            PaginationResponse<Artista> response = listArtistaUseCase.listAlbunsWithPaginationAndFilters(request,artistaFilter);
            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            throw new ConversionException("Valor de inteiro inválido", e);
        }

    }


    @Operation(summary = "Salvar artista",
            description = "Salva os dados do artista e retorna seus dados criados",
            tags = "Artista")
    @ApiResponse(responseCode = "201", description = "Artista criado")
    @PostMapping
    public ResponseEntity<Artista> saveEntity(@Valid @RequestBody SaveArtistaRequest request){
        Artista artistaSalvo = saveArtistaUseCase.salvarArtista(SaveArtistaDTOMapper.toDomain(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(artistaSalvo);

    }

    @Operation(summary = "Atualizar artista",
            description = "Atualiza os dados do artista e retorna seus dados atualizados",
            tags = "Artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artista atualizado"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })

    @PutMapping(value = "{id}")
    public ResponseEntity<Artista> updateEntity(@PathVariable String id,@Valid @RequestBody SaveArtistaRequest request){
        updateArtistaUseCase.updateArtista(SaveArtistaDTOMapper.toDomain(request), Integer.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
