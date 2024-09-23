package br.com.reserva.application.gateways;

import br.com.reserva.domain.entities.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ReservaGateway {

    Page<Reserva> buscaReservasPorRestaurante(Long restauranteId, Pageable pageable);
    Page<Reserva> buscaReservasPorRestaurantePeriodo(Long restauranteId, LocalDateTime periodoInicial, LocalDateTime periodoFinal, Pageable pageable);
    Page<Reserva> buscaReservasPorCliente(String clienteId, Pageable pageable);
    Page<Reserva> buscaReservasPorClientePeriodo(String clienteId, LocalDateTime periodoInicial, LocalDateTime periodoFinal, Pageable pageable);
    Reserva buscaReservaPorId(Long id);
    Reserva salvaReserva(Reserva reserva);
    Reserva atualizaReserva(Long id, Reserva reserva);

}
