package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.ReservaPresenter;
import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.application.usecases.cliente.VerificaExistenciaCliente;
import br.com.reserva.application.usecases.reserva.AtualizaMesa;
import br.com.reserva.application.usecases.reserva.ConsultaMesaDisponivel;
import br.com.reserva.application.usecases.reserva.RegistraReserva;
import br.com.reserva.application.usecases.reserva.ValidaReserva;
import br.com.reserva.application.usecases.restaurante.VerificaExistenciaRestaurante;
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

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class EfetuaReservaControllerTest {

    @Mock
    private ValidaReserva validaReserva;

    @Mock
    private VerificaExistenciaCliente verificaExistenciaCliente;

    @Mock
    private VerificaExistenciaRestaurante verificaExistenciaRestaurante;

    @Mock
    private ConsultaMesaDisponivel consultaMesaDisponivel;

    @Mock
    private RegistraReserva registraReserva;

    @Mock
    private AtualizaMesa atualizaMesa;

    @Mock
    private ReservaPresenter presenter;

    @InjectMocks
    private EfetuaReservaController efetuaReservaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoCriacaoReserva {

        @Test
        @DisplayName("deve lancar excecao para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            when(verificaExistenciaCliente.execute("teste@teste.com.br")).thenThrow(ClienteException.class);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(ClienteException.class);
        }

        @Test
        @DisplayName("deve lancar excecao para restaurante inexistente")
        public void deveLancarExcecaoParaRestauranteInexistente() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            when(verificaExistenciaCliente.execute("teste@teste.com.br")).thenReturn(cliente);
            when(verificaExistenciaRestaurante.execute(1L)).thenThrow(RestauranteException.class);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(RestauranteException.class);
        }

        @Test
        @DisplayName("deve lancar excecao para reserva invalida")
        public void deveLancarExcecaoParaReservaInvalida() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Endereco endereco =  new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            when(verificaExistenciaCliente.execute("teste@teste.com.br")).thenReturn(cliente);
            when(verificaExistenciaRestaurante.execute(1L)).thenReturn(restaurante);
            when(validaReserva.execute(reservaDTO, restaurante, mesa, cliente)).thenThrow(ReservaException.class);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(ReservaException.class);
        }

        @Test
        @DisplayName("deve lancar excecao para restaurante com ocupacao maxima")
        public void deveLancarExcecaoParaRestauranteComOcupacaoMaxima() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Endereco endereco =  new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            restaurante.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 1, restaurante, mesa, cliente);
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            when(verificaExistenciaCliente.execute("teste@teste.com.br")).thenReturn(cliente);
            when(verificaExistenciaRestaurante.execute(1L)).thenReturn(restaurante);
            when(validaReserva.execute(reservaDTO, restaurante, mesa, cliente)).thenReturn(reserva);
            when(consultaMesaDisponivel.execute(reservaDTO.getQuantidadePessoas(), 1L)).thenReturn(null);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("restaurante não possui mesa com a quantidade de pessoas solicitada");
        }

        @Test
        @DisplayName("deve lancar excecao para erro na criacao da reserva")
        public void deveLancarExcecaoParaErroNaCriacaoDaReserva() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Endereco endereco =  new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            restaurante.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 1, restaurante, mesa, cliente);
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            when(verificaExistenciaCliente.execute("teste@teste.com.br")).thenReturn(cliente);
            when(verificaExistenciaRestaurante.execute(1L)).thenReturn(restaurante);
            when(validaReserva.execute(reservaDTO, restaurante, mesa, cliente)).thenReturn(reserva);
            when(consultaMesaDisponivel.execute(reservaDTO.getQuantidadePessoas(), 1L)).thenReturn(mesa);
            when(registraReserva.execute(reserva)).thenReturn(null);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("erro ao salvar reserva");
        }

        @Test
        @DisplayName("deve efetuar reserva corretamente")
        public void deveEfetuarReservaCorretamente() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Endereco endereco =  new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            restaurante.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 1, restaurante, mesa, cliente);
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            when(verificaExistenciaCliente.execute("teste@teste.com.br")).thenReturn(cliente);
            when(verificaExistenciaRestaurante.execute(1L)).thenReturn(restaurante);
            when(validaReserva.execute(reservaDTO, restaurante, mesa, cliente)).thenReturn(reserva);
            when(consultaMesaDisponivel.execute(reservaDTO.getQuantidadePessoas(), 1L)).thenReturn(mesa);
            when(registraReserva.execute(reserva)).thenReturn(reserva);
            doNothing().when(atualizaMesa).execute(1L, StatusMesa.RESERVADA);
            when(presenter.execute(reserva)).thenReturn(reservaDTO);
            ReservaDTO reservaDTOReturned = efetuaReservaController.execute(reservaDTO);
            assertThat(reservaDTOReturned).isInstanceOf(ReservaDTO.class).isNotNull();
        }

    }

}
