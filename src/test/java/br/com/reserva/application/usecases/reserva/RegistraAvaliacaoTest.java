package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.AvaliacaoGateway;
import br.com.reserva.domain.entities.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegistraAvaliacaoTest {

    @Mock
    private AvaliacaoGateway avaliacaoGateway;

    @InjectMocks
    private RegistraAvaliacao registraAvaliacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidaRegistraAvaliacao {

        @Test
        @DisplayName("Deve registrar avaliacao corretamente")
        public void deveRegistrarAvaliacaoCorretamente() {
            Avaliacao avaliacao = new Avaliacao("titulo", 1, "comentario");
            when(avaliacaoGateway.salvaAvaliacao(avaliacao)).thenReturn(avaliacao);
            registraAvaliacao.execute(avaliacao);
            verify(avaliacaoGateway, times(1)).salvaAvaliacao(avaliacao);
        }

    }

}
