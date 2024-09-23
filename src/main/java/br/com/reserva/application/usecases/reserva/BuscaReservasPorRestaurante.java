package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaReservasPorRestaurante {

    private final ReservaGateway reservaGateway;

    @Autowired
    public BuscaReservasPorRestaurante(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Page<Reserva> execute(Long restauranteId, Pageable pageable) {
        return reservaGateway.buscaReservasPorRestaurante(restauranteId, pageable);
    }

}
