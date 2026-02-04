package br.com.hamix.infrastructure.controller.Regional;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.domain.model.Regional;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import br.com.hamix.infrastructure.controller.Regional.dto.RegionalRequest;
import br.com.hamix.infrastructure.controller.Regional.mapper.RegionalMapper;
import br.com.hamix.usecase.regional.atualizar.AtualizarRegionaisUseCase;
import br.com.hamix.usecase.regional.list.ListRegionalUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("regionais")
@Tag(name = "Regionais", description = "Endpoints de sincronização de regionais")
public class RegionalController {

    private final AtualizarRegionaisUseCase atualizarRegionaisUseCase;
    private final ListRegionalUseCase listRegionalUseCase;

    public RegionalController(AtualizarRegionaisUseCase atualizarRegionaisUseCase, ListRegionalUseCase listRegionalUseCase) {
        this.atualizarRegionaisUseCase = atualizarRegionaisUseCase;
        this.listRegionalUseCase = listRegionalUseCase;
    }

    @Operation(
            summary = "Atualizar regionais",
            description = "Importa uma a lista de regionais e atualiza as q não foram passadas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regionais sincronizadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @PostMapping("/atualizar")
    public ResponseEntity<?> atualizaRegionais(@Valid @RequestBody List<RegionalRequest> regionalRequests) {
        List<Regional> regionals = regionalRequests.stream()
                .map(RegionalMapper::toEntityFromRequest)
                        .toList();
        atualizarRegionaisUseCase.atualizar(regionals);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Buscar regionais",
            description = "Busca os regionais de maneira paginada e com filtros"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regionais encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao ler os dados de entrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<Regional>> getAll(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String name
    ) {
        try {
            PaginationRequest request = PaginationRequest.builder()
                    .page(Integer.valueOf(page))
                    .size(Integer.valueOf(size))
                    .sortBy(sortBy)
                    .sortDirection(sortDir)
                    .build();
            Regional regionalFilter = Regional.builder().nome(name).build();
            PaginationResponse<Regional> response = listRegionalUseCase
                    .listRegionaisWithPaginationAndFilters(request, regionalFilter);
            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            throw new ConversionException("Valor de inteiro inválido", e);
        }
    }
}
