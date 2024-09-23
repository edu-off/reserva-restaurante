package br.com.reserva.application.usecases.cliente;

import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VerificaExistenciaCliente {

    private final ClienteGateway clienteGateway;

    @Autowired
    public VerificaExistenciaCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(String clienteEmail) {
        Cliente cliente = clienteGateway.buscaClientePorId(clienteEmail);
        if (Objects.isNull(cliente))
            throw new EntityNotFoundException("cliente não encontrado");
        return cliente;
    }

}
