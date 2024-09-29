package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VerificaExistenciaRestaurante {

    private final RestauranteGateway restauranteGateway;

    @Autowired
    public VerificaExistenciaRestaurante(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    @Transactional
    public Restaurante execute(Long restauranteId) {
        Restaurante restaurante = restauranteGateway.buscaRestaurantePorId(restauranteId);
        if (Objects.isNull(restaurante))
            throw new RestauranteException("restaurante não encontrado");
        return restaurante;
    }

}
