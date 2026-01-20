package br.com.hamix.domain.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PaginationRequest {

    @Min(0)
    Integer page;

    @Min(1)
    @Max(100)
    Integer size;

    String sortBy;

    @Pattern(regexp = "ASC|DESC", message = "Direção do sort deve ser ASC ou DESC")
    String sortDirection;
}
