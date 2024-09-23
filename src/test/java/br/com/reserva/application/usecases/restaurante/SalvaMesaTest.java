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

import static org.mockito.Mockito.*;

public class SalvaMesaTest {

    @Mock
    private MesaGateway mesaGateway;

    @InjectMocks
    private SalvaMesa salvaMesa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoSalvaMesa {

        @Test
        @DisplayName("Deve salvar mesa corretamente")
        public void deveSalvarMesaCorretamente() {
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            when(mesaGateway.salvaMesa(mesa)).thenReturn(mesa);
            salvaMesa.execute(mesa);
            verify(mesaGateway, times(1)).salvaMesa(mesa);
        }

    }

}
