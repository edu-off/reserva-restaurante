package br.com.reserva.application.gateways;

import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;

public interface EnderecoGateway {

    Endereco salvaEndereco(Endereco endereco);

}
