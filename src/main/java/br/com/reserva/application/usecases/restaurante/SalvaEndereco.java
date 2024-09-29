package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.EnderecoGateway;
import br.com.reserva.domain.objectsvalue.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaEndereco {

    private final EnderecoGateway enderecoGateway;

    @Autowired
    public SalvaEndereco(EnderecoGateway enderecoGateway) {
        this.enderecoGateway = enderecoGateway;
    }

    @Transactional
    public Endereco execute(Endereco endereco) {
        return enderecoGateway.salvaEndereco(endereco);
    }

}
