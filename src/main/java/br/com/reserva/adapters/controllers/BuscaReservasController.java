package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.BuscaReservasPresenter;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorCliente;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorClientePeriodo;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorRestaurante;
import br.com.reserva.application.usecases.reserva.BuscaReservasPorRestaurantePeriodo;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class BuscaReservasController {

    private final BuscaReservasPorCliente buscaReservasPorCliente;
    private final BuscaReservasPorRestaurante buscaReservasPorRestaurante;
    private final BuscaReservasPorClientePeriodo buscaReservasPorClientePeriodo;
    private final BuscaReservasPorRestaurantePeriodo buscaReservasPorRestaurantePeriodo;
    private final BuscaReservasPresenter presenter;

    @Autowired
    public BuscaReservasController(BuscaReservasPorCliente buscaReservasPorCliente,
                                   BuscaReservasPorRestaurante buscaReservasPorRestaurante,
                                   BuscaReservasPorClientePeriodo buscaReservasPorClientePeriodo,
                                   BuscaReservasPorRestaurantePeriodo buscaReservasPorRestaurantePeriodo,
                                   BuscaReservasPresenter presenter) {
        this.buscaReservasPorCliente = buscaReservasPorCliente;
        this.buscaReservasPorRestaurante = buscaReservasPorRestaurante;
        this.buscaReservasPorClientePeriodo = buscaReservasPorClientePeriodo;
        this.buscaReservasPorRestaurantePeriodo = buscaReservasPorRestaurantePeriodo;
        this.presenter = presenter;
    }

    public Page<ReservaDTO> porCliente(String clienteId, Pageable pageable) {
        Page<Reserva> reservas = buscaReservasPorCliente.execute(clienteId, pageable);
        return presenter.execute(reservas, pageable);
    }

    public Page<ReservaDTO> porRestaurante(Long restauranteId, Pageable pageable) {
        Page<Reserva> reservas = buscaReservasPorRestaurante.execute(restauranteId, pageable);
        return presenter.execute(reservas, pageable);
    }

    public Page<ReservaDTO> porClientePeriodo(String clienteId, LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        Page<Reserva> reservas = buscaReservasPorClientePeriodo.execute(clienteId, inicio, fim, pageable);
        return presenter.execute(reservas, pageable);
    }

    public Page<ReservaDTO> porRestaurantePeriodo(Long restauranteId, LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        Page<Reserva> reservas = buscaReservasPorRestaurantePeriodo.execute(restauranteId, inicio, fim, pageable);
        return presenter.execute(reservas, pageable);
    }

}
