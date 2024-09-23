package br.com.reserva.application.factories;

import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.domain.entities.Avaliacao;

public class AvaliacaoFactory {

    public static Avaliacao createAvaliacao(AvaliacaoDTO avaliacaoDTO) throws IllegalArgumentException {
        return new Avaliacao(avaliacaoDTO.getTitulo(), avaliacaoDTO.getNota(), avaliacaoDTO.getComentario());
    }

}
