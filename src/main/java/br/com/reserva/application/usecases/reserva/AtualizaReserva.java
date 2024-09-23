package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizaReserva {

    private final ReservaGateway reservaGateway;

    @Autowired
    public AtualizaReserva(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Reserva execute(Long id, Reserva reserva) {
        return reservaGateway.atualizaReserva(id, reserva);
    }

}
