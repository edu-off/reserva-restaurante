package br.com.reserva.application.usecases.cliente;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.domain.entities.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static br.com.reserva.application.factories.ClienteFactory.createCliente;

@Service
public class ValidaCliente {

    @Transactional
    public Cliente execute(ClienteDTO clienteDTO) {
        Cliente cliente;
        try {
            cliente = createCliente(clienteDTO);
        } catch (IllegalArgumentException exception) {
            throw new ClienteException("erro ao validar dados do cliente: " + exception.getMessage());
        }
        return cliente;
    }

}
