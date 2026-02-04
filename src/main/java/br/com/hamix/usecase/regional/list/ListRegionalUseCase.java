package br.com.hamix.usecase.regional.list;

import br.com.hamix.domain.model.Regional;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;

public interface ListRegionalUseCase {
    PaginationResponse<Regional> listRegionaisWithPaginationAndFilters(
            PaginationRequest request,
            Regional toFilter
    );
}
