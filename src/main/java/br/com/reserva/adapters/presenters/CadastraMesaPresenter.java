package br.com.reserva.adapters.presenters;

import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.domain.entities.Mesa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastraMesaPresenter {

    private final ModelMapper mapper;

    @Autowired
    public CadastraMesaPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MesaDTO execute(Mesa mesa) {
        return mapper.map(mesa, MesaDTO.class);
    }

}
