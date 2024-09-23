package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.enums.StatusMesa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AtualizaMesaTest {

    @Mock
    private MesaGateway mesaGateway;

    @InjectMocks
    private AtualizaMesa atualizaMesa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidaAtualizaMesa {

        @Test
        @DisplayName("Deve ocupar mesa corretamente")
        public void deveOcuparMesaCorretamente() {
            Mesa mesa = new Mesa(1, StatusMesa.RESERVADA);
            when(mesaGateway.buscaMesaPorId(1L)).thenReturn(mesa);
            when(mesaGateway.atualizaMesa(1L, mesa)).thenReturn(mesa);
            atualizaMesa.execute(1L, StatusMesa.RESERVADA);
            verify(mesaGateway, times(1)).buscaMesaPorId(1L);
            verify(mesaGateway, times(1)).atualizaMesa(1L, mesa);
        }

        @Test
        @DisplayName("Deve lançar exceçâo quando a mesa nâo for encontrada")
        public void deveLancarExcecaoQuandoAMesaNaoForEncontrada() {
            when(mesaGateway.buscaMesaPorId(1L)).thenReturn(null);
            assertThatThrownBy(() -> atualizaMesa.execute(1L, StatusMesa.RESERVADA))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("erro ao ocupar mesa: mesa não encontrada");
        }

    }

}
