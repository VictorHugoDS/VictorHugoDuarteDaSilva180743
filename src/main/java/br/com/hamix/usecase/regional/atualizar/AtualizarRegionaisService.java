package br.com.hamix.usecase.regional.atualizar;

import br.com.hamix.domain.gateway.RegionalGateway;
import br.com.hamix.domain.model.Regional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtualizarRegionaisService implements AtualizarRegionaisUseCase {

    private final RegionalGateway regionalGateway;


    public AtualizarRegionaisService(RegionalGateway regionalGateway) {
        this.regionalGateway = regionalGateway;
    }

    // Segue as seguintes Regras
    // 1- Ativar todos os regionais informados na lista
    // 2- Substituir regionais já existentes
    // 3- Inativar regionais não informados

    @Override
    public void atualizar(List<Regional> regionals) {
        List<Regional> recebidos = new ArrayList<>();
        if (regionals != null) {
            for (Regional regional : regionals) {
                if (regional == null) {
                    continue;
                }
                String nome = regional.getNome();
                if (nome == null || nome.isBlank()) {
                    continue;
                }
                recebidos.add(Regional.builder()
                        .nome(nome)
                        .ativo(true)
                        .build());
            }
        }

        List<Regional> existentesAtivos = regionalGateway.findAllAtivos();
        List<Regional> paraSalvar = new ArrayList<>();

        // Regra 2 e 3: quem já existe é inativado e quem não foi passado também é inativado
        for (Regional existente : existentesAtivos) {
            // Sempre inativa os ativos atuais (se passou, regra 2; se não passou, regra 3)
            existente.setAtivo(false);
            paraSalvar.add(existente);
        }

        // Regra 1 e 2: todos os recebidos são criados/ativados
        paraSalvar.addAll(recebidos);

        if (!paraSalvar.isEmpty()) {
            regionalGateway.saveAll(paraSalvar);
        }
    }

}
