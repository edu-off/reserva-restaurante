package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.springframework.stereotype.Service;

import static br.com.reserva.application.factories.RestauranteFactory.createRestaurante;

@Service
public class ValidaRestaurante {

    public Restaurante execute(RestauranteDTO restauranteDTO, Endereco endereco) {
        Restaurante restaurante;
        try {
            restaurante = createRestaurante(restauranteDTO, endereco);
        } catch(IllegalArgumentException exception) {
            throw new RestauranteException("erro ao validar dados do restaurante: " + exception.getMessage());
        }
        return restaurante;
    }

}
