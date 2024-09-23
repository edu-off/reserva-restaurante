package br.com.reserva.application.gateways;

import br.com.reserva.domain.entities.Cliente;

public interface ClienteGateway {

    Cliente salvaCliente(Cliente cliente);
    Cliente buscaClientePorId(String id);

}
