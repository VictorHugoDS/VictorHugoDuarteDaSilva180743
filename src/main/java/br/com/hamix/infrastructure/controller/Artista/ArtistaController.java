package br.com.hamix.infrastructure.controller.Artista;

import br.com.hamix.domain.model.Artista;
import br.com.hamix.infrastructure.controller.Artista.dto.SaveArtistaRequest;
import br.com.hamix.infrastructure.controller.Artista.mapper.SaveArtistaDTOMapper;
import br.com.hamix.usecase.artista.criar.SaveArtistaUseCase;
import br.com.hamix.usecase.artista.get.GetArtistaPorIdUseCase;
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

    public ArtistaController(GetArtistaPorIdUseCase getArtistaPorIdUseCase, SaveArtistaUseCase saveArtistaUseCase) {
        this.getArtistaPorIdUseCase = getArtistaPorIdUseCase;
        this.saveArtistaUseCase = saveArtistaUseCase;
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
    @Operation(summary = "Salvar artista",
            description = "Salva os dados do artista e retorna seus dados criados",
            tags = "Artista")
    @ApiResponse(responseCode = "201", description = "Artista criado")
    @PostMapping
    public ResponseEntity<Artista> saveEntity(@Valid @RequestBody SaveArtistaRequest request){
        Artista artistaSalvo = saveArtistaUseCase.salvarArtista(SaveArtistaDTOMapper.toDomain(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(artistaSalvo);

    }
}
