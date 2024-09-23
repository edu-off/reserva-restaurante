package br.com.reserva.adapters.presenters;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastraRestaurantePresenter {

    private final ModelMapper mapper;

    @Autowired
    public CadastraRestaurantePresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public RestauranteDTO execute(Restaurante restaurante, Endereco endereco) {
        RestauranteDTO restauranteDTO = mapper.map(restaurante, RestauranteDTO.class);
        EnderecoDTO enderecoDTO = mapper.map(endereco, EnderecoDTO.class);
        restauranteDTO.setEndereco(enderecoDTO);
        return restauranteDTO;
    }

}
