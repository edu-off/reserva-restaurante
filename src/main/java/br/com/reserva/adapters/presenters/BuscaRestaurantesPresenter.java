package br.com.reserva.adapters.presenters;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuscaRestaurantesPresenter {

    private final ModelMapper mapper;

    @Autowired
    public BuscaRestaurantesPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Page<RestauranteDTO> execute(Page<Restaurante> restaurantes, Pageable pageable) {
        List<RestauranteDTO> restaurantesDTO = new ArrayList<>();
        restaurantes.getContent().parallelStream().forEach(restaurante -> restaurantesDTO.add(mapper.map(restaurante, RestauranteDTO.class)));
        return PageableExecutionUtils.getPage(restaurantesDTO, pageable, restaurantesDTO::size);
    }

}
