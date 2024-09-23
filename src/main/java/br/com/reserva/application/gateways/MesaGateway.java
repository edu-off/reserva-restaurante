package br.com.reserva.application.gateways;

import br.com.reserva.domain.entities.Mesa;

import java.util.List;

public interface MesaGateway {

    Mesa buscaMesaPorId(Long id);
    List<Mesa> buscaMesasPorRestaurante(Long restauranteId);
    Mesa salvaMesa(Mesa mesa);
    Mesa atualizaMesa(Long id, Mesa mesa);

}
