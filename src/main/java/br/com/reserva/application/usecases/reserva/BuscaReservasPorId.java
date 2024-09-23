package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BuscaReservasPorId {

    private final ReservaGateway reservaGateway;

    @Autowired
    public BuscaReservasPorId(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Reserva execute(Long id) {
        Reserva reserva = reservaGateway.buscaReservaPorId(id);
        if (Objects.isNull(reserva))
            throw new ReservaException("reserva não encontrada");
        return reserva;
    }

}
