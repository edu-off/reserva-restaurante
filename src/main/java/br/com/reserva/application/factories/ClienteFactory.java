package br.com.reserva.application.factories;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.domain.entities.Cliente;

public class ClienteFactory {

    public static Cliente createCliente(ClienteDTO clienteDTO) throws IllegalArgumentException {
        return new Cliente(clienteDTO.getEmail(), clienteDTO.getNome(), clienteDTO.getDdd(), clienteDTO.getTelefone());
    }

}
