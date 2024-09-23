package br.com.reserva.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AvaliacaoTest {

    private final String titulo = "título válido";
    private final String comentario = "comentário válido";
    private final Integer nota = 1;

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Avaliacao corretamente")
        public void deveInstanciarAvaliacaoCorretamente() {
            Avaliacao avaliacao = new Avaliacao(titulo, nota, comentario);
            assertThat(avaliacao).isInstanceOf(Avaliacao.class);
            assertThat(avaliacao.getId()).isNull();
            assertThat(avaliacao.getTitulo()).isEqualTo(titulo);
            assertThat(avaliacao.getNota()).isEqualTo(nota);
            assertThat(avaliacao.getComentario()).isEqualTo(comentario);
        }

    }

    @Nested
    public class ValidacaoTitulo {

        @Test
        @DisplayName("Deve lançar exceção para título nulo")
        public void deveLancarExcecaoParaTituloNulo() {
            String tituloNulo = null;
            assertThatThrownBy(() -> new Avaliacao(tituloNulo, nota, comentario))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("título inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para título em branco")
        public void deveLancarExcecaoParaTituloEmBranco() {
            String tituloEmBranco = "";
            assertThatThrownBy(() -> new Avaliacao(tituloEmBranco, nota, comentario))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("título inválido");
        }

    }

    @Nested
    public class ValidacaoNota {

        @Test
        @DisplayName("Deve lançar exceção para nota nula")
        public void deveLancarExcecaoParaNotaNula() {
            Integer notaNula = null;
            assertThatThrownBy(() -> new Avaliacao(titulo, notaNula, comentario))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nota inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para nota inferior ao minimo permitido")
        public void deveLancarExcecaoParaNotaInferiorAoMinimoPermitido() {
            Integer notaInferiorAoMinimoPermitido = 0;
            assertThatThrownBy(() -> new Avaliacao(titulo, notaInferiorAoMinimoPermitido, comentario))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nota inferior ao permitido");
        }

        @Test
        @DisplayName("Deve lançar exceção para nota superior ao máximo permitido")
        public void deveLancarExcecaoParaNotaSuperiorMaximoPermitido() {
            Integer notaSuperiorAoMaximoPermitido = 11;
            assertThatThrownBy(() -> new Avaliacao(titulo, notaSuperiorAoMaximoPermitido, comentario))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nota superior ao permitido");
        }

    }

    @Nested
    public class ValidacaoComentario {

        @Test
        @DisplayName("Deve lançar exceção para comentário nulo")
        public void deveLancarExcecaoParaComentarioNulo() {
            String comentarioNulo = null;
            assertThatThrownBy(() -> new Avaliacao(titulo, nota, comentarioNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("comentário inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para comentário em branco")
        public void deveLancarExcecaoParaComentarioEmBranco() {
            String comentarioEmBranco = "";
            assertThatThrownBy(() -> new Avaliacao(titulo, nota, comentarioEmBranco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("comentário inválido");
        }

    }

}
