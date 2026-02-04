package br.com.hamix.infrastructure.controller.Regional.mapper;

import br.com.hamix.domain.model.Regional;
import br.com.hamix.infrastructure.controller.Regional.dto.RegionalRequest;

public class RegionalMapper {
    public static Regional toEntityFromRequest (RegionalRequest request){
        return Regional.builder()
                .id(request.getId())
                .nome(request.getNome())
                .build();
    }

}
