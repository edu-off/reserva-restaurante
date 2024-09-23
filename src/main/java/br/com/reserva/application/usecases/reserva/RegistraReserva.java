package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistraReserva {

    private final ReservaGateway reservaGateway;

    @Autowired
    public RegistraReserva(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Reserva execute(Reserva reserva) {
        return reservaGateway.salvaReserva(reserva);
    }

}
