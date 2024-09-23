package br.com.reserva.domain.objectsvalue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnderecoTest {

    private final String logradouro = "logradouro válido";
    private final String numero = "numero válido";
    private final String bairro = "bairro válido";
    private final String cidade = "cidade válida";
    private final String uf = "SP";
    private final Long cep = 1L;

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Endereco corretamente")
        public void deveInstanciarEnderecoCorretamente() {
            Endereco endereco = new Endereco(logradouro, numero, bairro, cidade, uf, cep);
            assertThat(endereco).isInstanceOf(Endereco.class);
            assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
            assertThat(endereco.getNumero()).isEqualTo(numero);
            assertThat(endereco.getBairro()).isEqualTo(bairro);
            assertThat(endereco.getCidade()).isEqualTo(cidade);
            assertThat(endereco.getUf()).isEqualTo(uf);
            assertThat(endereco.getCep()).isEqualTo(cep);
        }

    }

    @Nested
    public class ValidacaoLogradouro {

        @Test
        @DisplayName("Deve lançar exceção para logradouro nulo")
        public void deveLancarExcecaoParaLogradouroNulo() {
            String logradouroNulo = null;
            assertThatThrownBy(() -> new Endereco(logradouroNulo, numero, bairro, cidade, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("logradouro inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para logradouro em branco")
        public void deveLancarExcecaoParaLogradouroEmBranco() {
            String logradouroEmBranco = "";
            assertThatThrownBy(() -> new Endereco(logradouroEmBranco, numero, bairro, cidade, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("logradouro inválido");
        }

    }

    @Nested
    public class ValidacaoNumero {

        @Test
        @DisplayName("Deve lançar exceção para número nulo")
        public void deveLancarExcecaoParaNumeroNulo() {
            String numeroNulo = null;
            assertThatThrownBy(() -> new Endereco(logradouro, numeroNulo, bairro, cidade, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("número inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para número em branco")
        public void deveLancarExcecaoParaNumeroEmBranco() {
            String numeroEmBranco = "";
            assertThatThrownBy(() -> new Endereco(logradouro, numeroEmBranco, bairro, cidade, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("número inválido");
        }

    }

    @Nested
    public class ValidacaoBairro {

        @Test
        @DisplayName("Deve lançar exceção para bairro nulo")
        public void deveLancarExcecaoParaBairroNulo() {
            String bairroNulo = null;
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairroNulo, cidade, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("bairro inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para bairro em branco")
        public void deveLancarExcecaoParaBairroEmBranco() {
            String bairroEmBranco = "";
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairroEmBranco, cidade, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("bairro inválido");
        }

    }

    @Nested
    public class ValidacaoCidade {

        @Test
        @DisplayName("Deve lançar exceção para cidade nula")
        public void deveLancarExcecaoParaCidadeNula() {
            String cidadeNula = null;
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidadeNula, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cidade inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para cidade em branco")
        public void deveLancarExcecaoParaCidadeEmBranco() {
            String cidadeEmBranco = "";
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidadeEmBranco, uf, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cidade inválida");
        }

    }

    @Nested
    public class ValidacaoUf {

        @Test
        @DisplayName("Deve lançar exceção para uf nula")
        public void deveLancarExcecaoParaUfNula() {
            String ufNula = null;
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidade, ufNula, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("uf inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para uf em branco")
        public void deveLancarExcecaoParaUfEmBranco() {
            String ufEmBranco = "";
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidade, ufEmBranco, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("uf inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para uf inválida")
        public void deveLancarExcecaoParaUfInvalida() {
            String ufInvalida = "XX";
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidade, ufInvalida, cep))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("uf inválida");
        }

    }

    @Nested
    public class ValidacaoCep {

        @Test
        @DisplayName("Deve lançar exceção para cep nulo")
        public void deveLancarExcecaoParaCepNulo() {
            Long cepNulo = null;
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidade, uf, cepNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cep inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para cep inferior ao minimo permitido")
        public void deveLancarExcecaoParaCepInferiorAoMinimoPermitido() {
            Long cepInferiorAoMinimoPermitido = 0L;
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidade, uf, cepInferiorAoMinimoPermitido))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cep inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para cep superior ao máximo permitido")
        public void deveLancarExcecaoParaCepSuperiorAoMaximoPermitido() {
            Long cepSuperiorAoMaximoPermitido = 100000000L;
            assertThatThrownBy(() -> new Endereco(logradouro, numero, bairro, cidade, uf, cepSuperiorAoMaximoPermitido))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cep inválido");
        }

    }



}
