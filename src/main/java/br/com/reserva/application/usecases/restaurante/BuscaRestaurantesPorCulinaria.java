package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaRestaurantesPorCulinaria {

    private final RestauranteGateway restauranteGateway;

    @Autowired
    public BuscaRestaurantesPorCulinaria(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Page<Restaurante> execute(String culinaria, Pageable pageable) {
        return restauranteGateway.buscaRestaurantesPorCulinaria(culinaria, pageable);
    }

}
