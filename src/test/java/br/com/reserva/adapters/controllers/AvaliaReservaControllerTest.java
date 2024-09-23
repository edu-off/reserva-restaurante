package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.application.exceptions.AvaliacaoException;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.usecases.reserva.RegistraAvaliacao;
import br.com.reserva.application.usecases.reserva.ValidaAvaliacao;
import br.com.reserva.application.usecases.reserva.VerificaExistenciaReserva;
import br.com.reserva.domain.entities.*;
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

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AvaliaReservaControllerTest {

    @Mock
    private VerificaExistenciaReserva verificaExistenciaReserva;

    @Mock
    private ValidaAvaliacao validaAvaliacao;

    @Mock
    private RegistraAvaliacao registraAvaliacao;

    @InjectMocks
    private AvaliaReservaController avaliaReservaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoAvaliacaoReserva {

        @Test
        @DisplayName("Deve lancar excecao na verificacao de existencia da reserva")
        public void deveLancarExcecaoNaVerificacaoDaExistenciaDaReserva() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");
            when(verificaExistenciaReserva.execute(1L)).thenThrow(ReservaException.class);
            assertThatThrownBy(() -> avaliaReservaController.execute(1L, avaliacaoDTO))
                    .isInstanceOf(ReservaException.class);
        }

        @Test
        @DisplayName("Deve lancar excecao na validacao da avaliacao")
        public void deveLancarExcecaoNaValidacaoDaAvaliacao() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Cliente cliente = new Cliente("teste@steste.com.br", "nome", 1, 1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 1, restaurante, mesa, cliente);
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");
            when(verificaExistenciaReserva.execute(1L)).thenReturn(reserva);
            when(validaAvaliacao.execute(avaliacaoDTO)).thenThrow(AvaliacaoException.class);
            assertThatThrownBy(() -> avaliaReservaController.execute(1L, avaliacaoDTO))
                    .isInstanceOf(AvaliacaoException.class);
        }

        @Test
        @DisplayName("Deve cadastrar cliente corretamente")
        public void deveCadastrarClienteCorretamente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Cliente cliente = new Cliente("teste@steste.com.br", "nome", 1, 1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 1, restaurante, mesa, cliente);
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");
            Avaliacao avaliacao = new Avaliacao("titulo", 1, "comentario");
            when(verificaExistenciaReserva.execute(1L)).thenReturn(reserva);
            when(validaAvaliacao.execute(avaliacaoDTO)).thenReturn(avaliacao);
            doNothing().when(registraAvaliacao).execute(avaliacao);
            avaliaReservaController.execute(1L, avaliacaoDTO);
            verify(registraAvaliacao, times(1)).execute(avaliacao);
        }

    }

}
