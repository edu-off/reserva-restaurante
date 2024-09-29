package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.BuscaRestaurantesPresenter;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.usecases.restaurante.BuscaRestaurantesPorCulinaria;
import br.com.reserva.application.usecases.restaurante.BuscaRestaurantesPorNome;
import br.com.reserva.domain.entities.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@Controller
public class BuscaRestaurantesController {

    private final BuscaRestaurantesPorNome buscaRestaurantesPorNome;
    private final BuscaRestaurantesPorCulinaria buscaRestaurantesPorCulinaria;
    private final BuscaRestaurantesPresenter presenter;

    @Autowired
    public BuscaRestaurantesController(BuscaRestaurantesPorNome buscaRestaurantesPorNome,
                                       BuscaRestaurantesPorCulinaria buscaRestaurantesPorCulinaria,
                                       BuscaRestaurantesPresenter presenter) {
        this.buscaRestaurantesPorNome = buscaRestaurantesPorNome;
        this.buscaRestaurantesPorCulinaria = buscaRestaurantesPorCulinaria;
        this.presenter = presenter;
    }

    public Page<RestauranteDTO> porNome(String nome, Pageable pageable) {
        Page<Restaurante> restaurantes = buscaRestaurantesPorNome.execute(nome, pageable);
        return presenter.execute(restaurantes, pageable);
    }

    public Page<RestauranteDTO> porCulinaria(String culinaria, Pageable pageable) {
        Page<Restaurante> restaurantes = buscaRestaurantesPorCulinaria.execute(culinaria, pageable);
        return presenter.execute(restaurantes, pageable);
    }

}
