package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.BuscaReservasPresenter;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorCliente;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorClientePeriodo;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorRestaurante;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorRestaurantePeriodo;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaReservasControllerTest {

    @Mock
    private BuscaReservasPorCliente buscaReservasPorCliente;

    @Mock
    private BuscaReservasPorRestaurante buscaReservasPorRestaurante;

    @Mock
    private BuscaReservasPorClientePeriodo buscaReservasPorClientePeriodo;

    @Mock
    private BuscaReservasPorRestaurantePeriodo buscaReservasPorRestaurantePeriodo;

    @Mock
    private BuscaReservasPresenter presenter;

    @InjectMocks
    private BuscaReservasController buscaReservasController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoDeBuscas {

        @Test
        @DisplayName("Deve buscar reservas por cliente")
        public void deveBuscarReservasPorCliente() {
            String email = "teste@teste.com.br";
            Pageable pageable = PageRequest.of(0, 10);
            Page<Reserva> pageReservaMocked = mockPageReservas();
            Page<ReservaDTO> pageReservasDTOMocked = mockPageReservasDTO();
            when(buscaReservasPorCliente.execute(email, pageable)).thenReturn(pageReservaMocked);
            when(presenter.execute(pageReservaMocked, pageable)).thenReturn(pageReservasDTOMocked);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porCliente(email, pageable);
            assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotNull().isEqualTo(pageReservasDTOMocked);
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante")
        public void deveBuscarReservasPorRestaurante() {
            Long id = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            Page<Reserva> pageReservaMocked = mockPageReservas();
            Page<ReservaDTO> pageReservasDTOMocked = mockPageReservasDTO();
            when(buscaReservasPorRestaurante.execute(id, pageable)).thenReturn(pageReservaMocked);
            when(presenter.execute(pageReservaMocked, pageable)).thenReturn(pageReservasDTOMocked);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porRestaurante(id, pageable);
            assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotNull().isEqualTo(pageReservasDTOMocked);
        }

        @Test
        @DisplayName("Deve buscar reservas por cliente e periodo")
        public void deveBuscarReservasPorClienteEPeriodo() {
            String email = "teste@teste.com.br";
            Pageable pageable = PageRequest.of(0, 10);
            LocalDateTime dateTime = LocalDateTime.now();
            Page<Reserva> pageReservaMocked = mockPageReservas();
            Page<ReservaDTO> pageReservasDTOMocked = mockPageReservasDTO();
            when(buscaReservasPorClientePeriodo.execute(email, dateTime, dateTime, pageable))
                    .thenReturn(pageReservaMocked);
            when(presenter.execute(pageReservaMocked, pageable)).thenReturn(pageReservasDTOMocked);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porClientePeriodo(email, dateTime, dateTime, pageable);
            assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotNull().isEqualTo(pageReservasDTOMocked);
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante e periodo")
        public void deveBuscarReservasPorRestauranteEPeriodo() {
            Long id = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            LocalDateTime dateTime = LocalDateTime.now();
            Page<Reserva> pageReservaMocked = mockPageReservas();
            Page<ReservaDTO> pageReservasDTOMocked = mockPageReservasDTO();
            when(buscaReservasPorRestaurantePeriodo.execute(id, dateTime, dateTime, pageable))
                    .thenReturn(pageReservaMocked);
            when(presenter.execute(pageReservaMocked, pageable)).thenReturn(pageReservasDTOMocked);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porRestaurantePeriodo(id, dateTime, dateTime, pageable);
            assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotNull().isEqualTo(pageReservasDTOMocked);
        }

        private Page<Reserva> mockPageReservas() {
            Pageable pageable = PageRequest.of(0, 10);
            Restaurante restaurante = new Restaurante();
            restaurante.setId(1L);
            Cliente cliente = new Cliente();
            cliente.setEmail("teste@teste.com.br");
            LocalDateTime dateTime = LocalDateTime.now().plusHours(7);
            Mesa mesa1 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa1.setId(1L);
            Mesa mesa2 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa2.setId(2L);
            Mesa mesa3 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa3.setId(3L);
            Reserva reserva1 = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa1, cliente);
            Reserva reserva2 = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa2, cliente);
            Reserva reserva3 = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa3, cliente);
            List<Reserva> reservas = List.of(reserva1, reserva2, reserva3);
            return PageableExecutionUtils.getPage(reservas, pageable, reservas::size);
        }

        private Page<ReservaDTO> mockPageReservasDTO() {
            Pageable pageable = PageRequest.of(0, 10);
            LocalDateTime dateTime = LocalDateTime.now();
            ReservaDTO reservaDTO1 = new ReservaDTO(null, "DISPONIVEL", dateTime, 2, null, null, null);
            ReservaDTO reservaDTO2 = new ReservaDTO(null, "DISPONIVEL", dateTime, 2, null, null, null);
            ReservaDTO reservaDTO3 = new ReservaDTO(null, "DISPONIVEL", dateTime, 2, null, null, null);
            List<ReservaDTO> reservas = List.of(reservaDTO1, reservaDTO2, reservaDTO3);
            return PageableExecutionUtils.getPage(reservas, pageable, reservas::size);
        }

    }

}
