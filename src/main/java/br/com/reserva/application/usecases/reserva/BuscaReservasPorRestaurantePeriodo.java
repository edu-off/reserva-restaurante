package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BuscaReservasPorRestaurantePeriodo {

    private final ReservaGateway reservaGateway;

    @Autowired
    public BuscaReservasPorRestaurantePeriodo(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Page<Reserva> execute(Long restauranteId, LocalDateTime periodoInicial, LocalDateTime periodoFinal, Pageable pageable) {
        return reservaGateway.buscaReservasPorRestaurantePeriodo(restauranteId, periodoInicial, periodoFinal, pageable);
    }

}
