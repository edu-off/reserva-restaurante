package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.CadastraRestaurantePresenter;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.usecases.restaurante.SalvaEndereco;
import br.com.reserva.application.usecases.restaurante.SalvaRestaurante;
import br.com.reserva.application.usecases.restaurante.ValidaEndereco;
import br.com.reserva.application.usecases.restaurante.ValidaRestaurante;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CadastraRestauranteController {

    private final ValidaEndereco validaEndereco;
    private final ValidaRestaurante validaRestaurante;
    private final SalvaRestaurante salvaRestaurante;
    private final SalvaEndereco salvaEndereco;
    private final CadastraRestaurantePresenter presenter;

    @Autowired
    public CadastraRestauranteController(ValidaEndereco validaEndereco, ValidaRestaurante validaRestaurante,
                                         SalvaRestaurante salvaRestaurante, SalvaEndereco salvaEndereco,
                                         CadastraRestaurantePresenter presenter) {
        this.validaEndereco = validaEndereco;
        this.validaRestaurante = validaRestaurante;
        this.salvaRestaurante = salvaRestaurante;
        this.salvaEndereco = salvaEndereco;
        this.presenter = presenter;
    }

    public RestauranteDTO execute(RestauranteDTO restauranteDTO) {
        Endereco endereco = validaEndereco.execute(restauranteDTO.getEndereco());
        Restaurante restaurante = validaRestaurante.execute(restauranteDTO, endereco);
        restaurante.setEndereco(null);
        Restaurante newRestaurante = salvaRestaurante.execute(restaurante);
        endereco.setRestaurante(newRestaurante);
        Endereco newEndereco = salvaEndereco.execute(endereco);
        return presenter.execute(newRestaurante, newEndereco);
    }

}
