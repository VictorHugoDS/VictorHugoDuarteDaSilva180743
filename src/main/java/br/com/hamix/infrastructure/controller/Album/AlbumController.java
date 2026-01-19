package br.com.hamix.infrastructure.controller.Album;

import br.com.hamix.domain.model.Album;
import br.com.hamix.infrastructure.controller.Album.dto.SaveAlbumRequest;
import br.com.hamix.infrastructure.controller.Album.mapper.SaveAlbumDTOMapper;
import br.com.hamix.usecase.album.criar.SaveAlbumUseCase;
import br.com.hamix.usecase.album.get.GetAlbumPorIdUseCase;
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

    public AlbumController(GetAlbumPorIdUseCase getAlbumPorIdUseCase, SaveAlbumUseCase saveAlbumUseCase) {
        this.getAlbumPorIdUseCase = getAlbumPorIdUseCase;
        this.saveAlbumUseCase = saveAlbumUseCase;
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
    @Operation(summary = "Salvar album",
            description = "Salva os dados do album e retorna seus dados criados",
            tags = "Album")
    @ApiResponse(responseCode = "201", description = "Album criado")
    @PostMapping
    public ResponseEntity<Album> saveEntity(@Valid @RequestBody SaveAlbumRequest request){
        Album albumSalvo = saveAlbumUseCase.salvarAlbum(SaveAlbumDTOMapper.toDomain(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(albumSalvo);

    }
}
