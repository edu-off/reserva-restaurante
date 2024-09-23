package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.AvaliacaoGateway;
import br.com.reserva.domain.entities.Avaliacao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AvaliacaoGatewayIT {

    @Autowired
    private AvaliacaoGateway avaliacaoGateway;

    @Nested
    public class ValidacaoAvaliacaoGateway {

        @Test
        @DisplayName("Deve salvar dominio avaliacao")
        public void deveSalvarDominioAvaliacao() {
            Avaliacao avaliacao = new Avaliacao("titulo", 1, "comentario");
            Avaliacao avaliacaoCreated = avaliacaoGateway.salvaAvaliacao(avaliacao);
            assertThat(avaliacaoCreated).isInstanceOf(Avaliacao.class).isNotNull();
            assertThat(avaliacaoCreated.getId()).isNotNull().isNotZero();
            assertThat(avaliacaoCreated.getTitulo()).isEqualTo(avaliacao.getTitulo());
            assertThat(avaliacaoCreated.getNota()).isEqualTo(avaliacao.getNota());
            assertThat(avaliacaoCreated.getComentario()).isEqualTo(avaliacao.getComentario());
            assertThat(avaliacaoCreated.getReserva()).isNull();
        }

    }

}
