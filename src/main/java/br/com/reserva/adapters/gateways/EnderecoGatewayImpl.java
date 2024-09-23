package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.EnderecoGateway;
import br.com.reserva.domain.objectsvalue.Endereco;
import br.com.reserva.infrastructure.database.models.EnderecoModel;
import br.com.reserva.infrastructure.database.repositories.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoGatewayImpl implements EnderecoGateway {

    private final EnderecoRepository enderecoRepository;
    private final ModelMapper mapper;

    @Autowired
    public EnderecoGatewayImpl(EnderecoRepository enderecoRepository, ModelMapper mapper) {
        this.enderecoRepository = enderecoRepository;
        this.mapper = mapper;
    }

    @Override
    public Endereco salvaEndereco(Endereco endereco) {
        EnderecoModel enderecoModel = mapper.map(endereco, EnderecoModel.class);
        enderecoModel.setId(null);
        EnderecoModel enderecoModelCreated = enderecoRepository.save(enderecoModel);
        return mapper.map(enderecoModelCreated, Endereco.class);
    }
}
