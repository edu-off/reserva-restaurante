package br.com.reserva.application.usecases.cliente;

import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.domain.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaCliente {

    private ClienteGateway clienteGateway;

    @Autowired
    public SalvaCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(Cliente cliente) {
        return clienteGateway.salvaCliente(cliente);
    }

}
