package br.com.reserva.adapters.presenters;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.domain.entities.Reserva;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservaPresenter {

    private final ModelMapper mapper;

    @Autowired
    public ReservaPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ReservaDTO execute(Reserva reserva) {
        return mapper.map(reserva, ReservaDTO.class);
    }

}
