package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaRestaurantesPorNome {

    private final RestauranteGateway restauranteGateway;

    @Autowired
    public BuscaRestaurantesPorNome(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    @Transactional
    public Page<Restaurante> execute(String nome, Pageable pageable) {
        return restauranteGateway.buscaRestaurantesPorNome(nome, pageable);
    }

}
