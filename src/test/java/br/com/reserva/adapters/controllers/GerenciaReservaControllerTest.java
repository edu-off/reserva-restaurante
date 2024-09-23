package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.ReservaPresenter;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.usecases.reserva.*;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class GerenciaReservaControllerTest {

    @Mock
    private BuscaReservasPorId buscaReservasPorId;

    @Mock
    private AlteraDadosReserva alteraDadosReserva;

    @Mock
    private ValidaReserva validaReserva;

    @Mock
    private AtualizaReserva atualizaReserva;

    @Mock
    private AtualizaMesa atualizaMesa;

    @Mock
    private ReservaPresenter presenter;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private GerenciaReservaController gerenciaReservaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoGerenciamentoReserva {

        @Test
        @DisplayName("Deve lancar excecao para reserva nao encontrada")
        public void deveLancarExcecaoParaReservaNaoEncontrada() {
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            ReservaDTO reservaDTO = new ReservaDTO(1L, "ATIVA", LocalDateTime.now().plusHours(7), 1, null, null, null);
            when(buscaReservasPorId.execute(1L)).thenThrow(ReservaException.class);
            assertThatThrownBy(() -> gerenciaReservaController.execute(1L, reservaDTO))
                    .isInstanceOf(ReservaException.class);
        }

        @Test
        @DisplayName("Deve ativar reserva corretamente")
        public void deveAtivarReservaCorretamente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Reserva reservaOld = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            Reserva reservaNew = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            ReservaDTO reservaDTO = new ReservaDTO(1L, "ATIVA", LocalDateTime.now().plusHours(7), 1, null, null, null);
            when(buscaReservasPorId.execute(1L)).thenReturn(reservaOld);
            when(alteraDadosReserva.execute(reservaOld, reservaDTO)).thenReturn(reservaNew);
            when(mapper.map(reservaNew, ReservaDTO.class)).thenReturn(reservaDTO);
            when(validaReserva.execute(reservaDTO, restaurante, mesa, cliente)).thenReturn(reservaNew);
            when(atualizaReserva.execute(1L, reservaNew)).thenReturn(reservaNew);
            doNothing().when(atualizaMesa).execute(1L, mesa.getStatus());
            when(presenter.execute(reservaNew)).thenReturn(reservaDTO);
            ReservaDTO reservaDTOChanged = gerenciaReservaController.execute(1L, reservaDTO);
            assertThat(reservaDTOChanged).isInstanceOf(ReservaDTO.class).isNotNull();
            assertThat(reservaDTOChanged.getStatus()).isEqualTo("ATIVA");
        }

        @Test
        @DisplayName("Deve cancelar reserva corretamente")
        public void deveCancelarrReservaCorretamente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Reserva reservaOld = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            Reserva reservaNew = new Reserva(StatusReserva.CANCELADA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            ReservaDTO reservaDTO = new ReservaDTO(1L, "CANCELADA", LocalDateTime.now().plusHours(7), 1, null, null, null);
            when(buscaReservasPorId.execute(1L)).thenReturn(reservaOld);
            when(alteraDadosReserva.execute(reservaOld, reservaDTO)).thenReturn(reservaNew);
            when(mapper.map(reservaNew, ReservaDTO.class)).thenReturn(reservaDTO);
            when(validaReserva.execute(reservaDTO, restaurante, mesa, cliente)).thenReturn(reservaNew);
            when(atualizaReserva.execute(1L, reservaNew)).thenReturn(reservaNew);
            doNothing().when(atualizaMesa).execute(1L, mesa.getStatus());
            when(presenter.execute(reservaNew)).thenReturn(reservaDTO);
            ReservaDTO reservaDTOChanged = gerenciaReservaController.execute(1L, reservaDTO);
            assertThat(reservaDTOChanged).isInstanceOf(ReservaDTO.class).isNotNull();
            assertThat(reservaDTOChanged.getStatus()).isEqualTo("CANCELADA");
        }

    }

}
