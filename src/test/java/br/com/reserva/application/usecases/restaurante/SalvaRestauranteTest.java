package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SalvaRestauranteTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private SalvaRestaurante salvaRestaurante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoSalvaRestaurante {

        @Test
        @DisplayName("Deve retornar restaurante corretamente")
        public void deveRetornarRestauranteCorretamente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            when(restauranteGateway.salvaRestaurante(restaurante)).thenReturn(restaurante);
            restaurante = salvaRestaurante.execute(restaurante);
            assertThat(restaurante).isInstanceOf(Restaurante.class).isNotNull();
        }

    }

}
