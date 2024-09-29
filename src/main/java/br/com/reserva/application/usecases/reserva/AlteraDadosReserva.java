package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.enums.StatusReserva;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AlteraDadosReserva {

    @Transactional
    public Reserva execute(Reserva reserva, ReservaDTO reservaDTO) {
        reserva.setStatus(StatusReserva.valueOf(reservaDTO.getStatus()));
        reserva.setAgendamento(reservaDTO.getAgendamento());
        reserva.setQuantidadePessoas(reservaDTO.getQuantidadePessoas());
        return reserva;
    }
}
