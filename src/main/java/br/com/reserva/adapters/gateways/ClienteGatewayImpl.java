package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.infrastructure.database.models.ClienteModel;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;
    private final ModelMapper mapper;

    @Autowired
    public ClienteGatewayImpl(ClienteRepository clienteRepository, ModelMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public Cliente salvaCliente(Cliente cliente) {
        ClienteModel clienteModel = mapper.map(cliente, ClienteModel.class);
        ClienteModel clienteModelCreated = clienteRepository.save(clienteModel);
        return mapper.map(clienteModelCreated, Cliente.class);
    }

    @Override
    public Cliente buscaClientePorId(String id) {
        Optional<ClienteModel> optionalClienteModel = clienteRepository.findById(id);
        return optionalClienteModel.map(clienteModel -> mapper.map(clienteModel, Cliente.class)).orElse(null);
    }

}
