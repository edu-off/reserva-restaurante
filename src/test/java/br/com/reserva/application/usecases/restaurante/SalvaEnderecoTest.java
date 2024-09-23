package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.EnderecoGateway;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SalvaEnderecoTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @InjectMocks
    private SalvaEndereco salvaEndereco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoSalvaEndereco {

        @Test
        @DisplayName("Deve retornar endereco corretamente")
        public void deveRetornarRestauranteCorretamente() {
            Endereco endereco = new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            when(enderecoGateway.salvaEndereco(endereco)).thenReturn(endereco);
            endereco = salvaEndereco.execute(endereco);
            assertThat(endereco).isInstanceOf(Endereco.class).isNotNull();
        }

    }

}
