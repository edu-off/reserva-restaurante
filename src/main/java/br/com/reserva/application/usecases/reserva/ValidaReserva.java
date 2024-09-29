package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusReserva;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.com.reserva.application.factories.ReservaFactory.createReserva;

@Service
public class ValidaReserva {

    @Transactional
    public Reserva execute(ReservaDTO reservaDTO, Restaurante restaurante, Mesa mesa, Cliente cliente) {
        if (Objects.isNull(StatusReserva.getValue(reservaDTO.getStatus())))
            throw new ReservaException("erro ao registrar reserva: status inválido");
        Reserva reserva;
        try {
            reserva = createReserva(reservaDTO, restaurante, mesa, cliente);
        } catch(IllegalArgumentException exception) {
            throw new ReservaException("erro ao registrar reserva: " + exception.getMessage());
        }
        return reserva;
    }

}
