package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.AvaliacaoGateway;
import br.com.reserva.domain.entities.Avaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistraAvaliacao {

    private final AvaliacaoGateway avaliacaoGateway;

    @Autowired
    public RegistraAvaliacao(AvaliacaoGateway avaliacaoGateway) {
        this.avaliacaoGateway = avaliacaoGateway;
    }

    public void execute(Avaliacao avaliacao) {
        avaliacaoGateway.salvaAvaliacao(avaliacao);
    }

}
