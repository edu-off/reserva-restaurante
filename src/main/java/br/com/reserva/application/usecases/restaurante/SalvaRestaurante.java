package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaRestaurante {

    private final RestauranteGateway restauranteGateway;

    @Autowired
    public SalvaRestaurante(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante execute(Restaurante restaurante) {
        return restauranteGateway.salvaRestaurante(restaurante);
    }

}
