package br.com.reserva.application.gateways;

import br.com.reserva.domain.entities.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestauranteGateway {

    Page<Restaurante> buscaRestaurantesPorNome(String nome, Pageable pageable);
    Page<Restaurante> buscaRestaurantesPorCulinaria(String culinaria, Pageable pageable);
    Restaurante buscaRestaurantePorId(Long id);
    Restaurante salvaRestaurante(Restaurante restaurante);
    Restaurante atualizaRestaurante(Long id, Restaurante restaurante);

}
