package br.com.reserva.adapters.presenters;

import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuscaReservasPresenter {

    private final ModelMapper mapper;

    @Autowired
    public BuscaReservasPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Page<ReservaDTO> execute(Page<Reserva> reservas, Pageable pageable) {
        List<ReservaDTO> reservasDTO = new ArrayList<>();
        reservas.getContent().parallelStream().forEach(reserva -> reservasDTO.add(mapper.map(reserva, ReservaDTO.class)));
        return PageableExecutionUtils.getPage(reservasDTO, pageable, reservasDTO::size);
    }

}
