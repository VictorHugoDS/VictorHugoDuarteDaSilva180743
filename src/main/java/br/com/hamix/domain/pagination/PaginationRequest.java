package br.com.hamix.domain.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PaginationRequest {
    Integer page;
    Integer size;
    String sortBy;
    String sortDirection;
}
