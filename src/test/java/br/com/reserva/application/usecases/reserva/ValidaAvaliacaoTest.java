package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.exceptions.AvaliacaoException;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.usecases.cliente.ValidaCliente;
import br.com.reserva.domain.entities.Avaliacao;
import br.com.reserva.domain.entities.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidaAvaliacaoTest {

    private final ValidaAvaliacao validaAvaliacao = new ValidaAvaliacao();

    @Nested
    public class ValidacaoTransformacaoDtoParaDominio {

        @Test
        @DisplayName("Deve lançar exceção para avaliacao com algum dado invalido")
        public void deveLancarExcecaoParaAvaliacaoComAlgumDadoInvalido() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "", 1, "comentario");
            Assertions.assertThatThrownBy(() -> validaAvaliacao.execute(avaliacaoDTO))
                    .isInstanceOf(AvaliacaoException.class)
                    .hasMessage("erro ao validar dados da avaliaçâo: título inválido");
        }

        @Test
        @DisplayName("Deve retornar endereço corretamente")
        public void deveRetornarEnderecoCorretamente() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");
            Avaliacao avaliacao = validaAvaliacao.execute(avaliacaoDTO);
            assertThat(avaliacao).isInstanceOf(Avaliacao.class).isNotNull();
            assertThat(avaliacao.getId()).isNull();
            assertThat(avaliacao.getTitulo()).isEqualTo("titulo");
            assertThat(avaliacao.getNota()).isEqualTo(1);
            assertThat(avaliacao.getComentario()).isEqualTo("comentario");
        }

    }

}
