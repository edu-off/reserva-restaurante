package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.exceptions.EnderecoException;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.springframework.stereotype.Service;

import static br.com.reserva.application.factories.EnderecoFactory.createEndereco;

@Service
public class ValidaEndereco {

    public Endereco execute(EnderecoDTO enderecoDTO) {
        Endereco endereco;
        try {
            endereco = createEndereco(enderecoDTO);
        } catch(IllegalArgumentException exception) {
            throw new EnderecoException("erro ao validar dados de endereço: " + exception.getMessage());
        }
        return endereco;
    }

}
