package br.com.hamix.usecase.regional.list;

import br.com.hamix.domain.gateway.RegionalGateway;
import br.com.hamix.domain.model.Regional;
import br.com.hamix.domain.pagination.PaginationRequest;
import br.com.hamix.domain.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListRegionalService implements ListRegionalUseCase {

    private final RegionalGateway regionalGateway;

    public ListRegionalService(RegionalGateway regionalGateway) {
        this.regionalGateway = regionalGateway;
    }

    @Override
    public PaginationResponse<Regional> listRegionaisWithPaginationAndFilters(
            PaginationRequest request,
            Regional toFilter
    ) {
        Page<Regional> page = regionalGateway.getPage(request, toFilter);
        return new PaginationResponse<>(
                page.toList(),
                String.valueOf(page.getNumber()),
                String.valueOf(page.getSize()),
                String.valueOf(page.getTotalPages()),
                String.valueOf(page.getTotalElements())
        );
    }
}
