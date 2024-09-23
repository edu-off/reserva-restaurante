package br.com.reserva.application.factories;

import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.enums.StatusMesa;

public class MesaFactory {

    public static Mesa createMesa(MesaDTO mesaDTO, StatusMesa status) throws IllegalArgumentException {
        return new Mesa(mesaDTO.getCapacidade(), status);
    }

}
