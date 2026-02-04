package br.com.hamix.domain.gateway;

import br.com.hamix.domain.model.Regional;
import br.com.hamix.domain.pagination.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RegionalGateway {
    List<Regional> findAllAtivos();
    List<Regional> saveAll(List<Regional> regionais);
    Page<Regional> getPage(PaginationRequest pagination, Regional toFilter);
}