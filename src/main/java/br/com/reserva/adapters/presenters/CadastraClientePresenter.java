package br.com.reserva.adapters.presenters;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.domain.entities.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastraClientePresenter {

    private final ModelMapper mapper;

    @Autowired
    public CadastraClientePresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ClienteDTO execute(Cliente cliente) {
        return mapper.map(cliente, ClienteDTO.class);
    }

}
