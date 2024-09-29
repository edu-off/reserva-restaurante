package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VerificaExistenciaReserva {

    private final ReservaGateway reservaGateway;

    @Autowired
    public VerificaExistenciaReserva(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    @Transactional
    public Reserva execute(Long reservaId) {
        Reserva reserva = reservaGateway.buscaReservaPorId(reservaId);
        if (Objects.isNull(reserva))
            throw new EntityNotFoundException("reserva não encontrada");
        return reserva;
    }

}
