package br.com.hamix.infrastructure.gateways.mappers;

import br.com.hamix.domain.pagination.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationMapper {
    public static Pageable toPageable(PaginationRequest request) {
        Sort.Direction direction = Sort.Direction.fromString(request.getSortDirection());
        Sort sort = Sort.by(direction, request.getSortBy());
        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }

}
