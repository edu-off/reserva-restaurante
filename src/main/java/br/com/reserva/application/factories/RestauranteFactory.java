package br.com.reserva.application.factories;

import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;

public class RestauranteFactory {

    public static Restaurante createRestaurante(RestauranteDTO restauranteDTO, Endereco endereco) throws IllegalArgumentException {
        return new Restaurante(restauranteDTO.getNome(), restauranteDTO.getCulinaria(), restauranteDTO.getCapacidade(),
                restauranteDTO.getHorarioAbertura(), restauranteDTO.getHorarioEncerramento(), restauranteDTO.getDdd(),
                restauranteDTO.getTelefone(), endereco);
    }

}
