package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ConsultaMesaDisponivelTest {

    @Mock
    private MesaGateway mesaGateway;

    @InjectMocks
    private ConsultaMesaDisponivel consultaMesaDisponivel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidaConsultaMesaDisponivel {

        @Test
        @DisplayName("Deve retornar id valido de mesa disponivel")
        public void deveRetornarIdValidoDeMesaDisponivel() {
            Restaurante restaurante = getRestaurante();
            when(mesaGateway.buscaMesasPorRestaurante(restaurante.getId())).thenReturn(restaurante.getMesas());
            assertThat(consultaMesaDisponivel.execute(1, restaurante.getId())).isInstanceOf(Mesa.class).isNotNull();
        }

        @Test
        @DisplayName("Deve retornar id nulo de mesa disponivel")
        public void deveRetornarIdnuloDeMesaDisponivel() {
            Restaurante restaurante = getRestaurante();
            when(mesaGateway.buscaMesasPorRestaurante(restaurante.getId())).thenReturn(restaurante.getMesas());
            assertThat(consultaMesaDisponivel.execute(2, restaurante.getId())).isNull();
        }

        private Restaurante getRestaurante() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Mesa mesa1 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa2 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa3 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa4 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa1.setId(1L);
            mesa2.setId(2L);
            mesa3.setId(3L);
            mesa4.setId(4L);
            restaurante.setMesas(List.of(mesa1, mesa2, mesa3, mesa4));
            return restaurante;
        }

    }

}
