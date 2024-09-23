package br.com.reserva.application.factories;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.domain.objectsvalue.Endereco;

public class EnderecoFactory {

    public static Endereco createEndereco(EnderecoDTO enderecoDTO) throws IllegalArgumentException {
        return new Endereco(enderecoDTO.getLogradouro(), enderecoDTO.getNumero(), enderecoDTO.getBairro(),
                enderecoDTO.getCidade(), enderecoDTO.getUf(), enderecoDTO.getCep());
    }

}
