package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.application.exceptions.AvaliacaoException;
import br.com.reserva.application.exceptions.EnderecoException;
import br.com.reserva.domain.entities.Avaliacao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static br.com.reserva.application.factories.AvaliacaoFactory.createAvaliacao;

@Service
public class ValidaAvaliacao {

    @Transactional
    public Avaliacao execute(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao;
        try {
            avaliacao = createAvaliacao(avaliacaoDTO);
        } catch(IllegalArgumentException exception) {
            throw new AvaliacaoException("erro ao validar dados da avaliaçâo: " + exception.getMessage());
        }
        return avaliacao;
    }

}
