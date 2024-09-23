package br.com.reserva.application.factories;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusReserva;

public class ReservaFactory {

    public static Reserva createReserva(ReservaDTO reservaDTO, Restaurante restaurante, Mesa mesa, Cliente cliente) throws IllegalArgumentException {
        return new Reserva(StatusReserva.valueOf(reservaDTO.getStatus()), reservaDTO.getAgendamento(), reservaDTO.getQuantidadePessoas(), restaurante, mesa, cliente);
    }

}
