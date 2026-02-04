package br.com.hamix.usecase.regional.atualizar;

import br.com.hamix.domain.model.Regional;

import java.util.List;

public interface AtualizarRegionaisUseCase {
    void atualizar(List<Regional> regionals);
}
