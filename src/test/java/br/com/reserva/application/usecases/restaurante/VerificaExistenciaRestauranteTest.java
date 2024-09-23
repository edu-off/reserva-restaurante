package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VerificaExistenciaRestauranteTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private VerificaExistenciaRestaurante verificaExistenciaRestaurante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoSalvaMesa {

        @Test
        @DisplayName("Deve retornar restaurante")
        public void deveRetornarRestaurante() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            when(restauranteGateway.buscaRestaurantePorId(restaurante.getId())).thenReturn(restaurante);
            Restaurante restauranteFinded = verificaExistenciaRestaurante.execute(restaurante.getId());
            assertThat(restauranteFinded).isInstanceOf(Restaurante.class).isNotNull().isEqualTo(restaurante);
            verify(restauranteGateway, times(1)).buscaRestaurantePorId(restaurante.getId());
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante inexistente")
        public void deveLançarExcecaoParaRestauranteInexistente() {
            Long id = 1L;
            when(restauranteGateway.buscaRestaurantePorId(id)).thenReturn(null);
            Assertions.assertThatThrownBy(() -> verificaExistenciaRestaurante.execute(id))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("restaurante não encontrado");
        }

    }

}
