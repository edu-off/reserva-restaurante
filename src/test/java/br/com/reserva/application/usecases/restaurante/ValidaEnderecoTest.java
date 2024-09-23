package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.exceptions.EnderecoException;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidaEnderecoTest {

    private final ValidaEndereco validaEndereco = new ValidaEndereco();
    private final String logradouro = "logradouro válido";
    private final String numero = "numero válido";
    private final String bairro = "bairro válido";
    private final String cidade = "cidade válida";
    private final String uf = "SP";
    private final Long cep = 1L;

    @Nested
    public class ValidacaoTransformacaoDtoParaDominio {

        @Test
        @DisplayName("Deve lançar exceção para contato com logradouro inválido")
        public void deveLancarExcecaoParaContatoComLogradouroInvalido() {
            String logradouroInvalido = "";
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouroInvalido, numero, bairro, cidade, uf, cep);
            Assertions.assertThatThrownBy(() -> validaEndereco.execute(enderecoDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar dados de endereço: logradouro inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para contato com número inválido")
        public void deveLancarExcecaoParaContatoComNumeroInvalido() {
            String numeroInvalido = "";
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouro, numeroInvalido, bairro, cidade, uf, cep);
            Assertions.assertThatThrownBy(() -> validaEndereco.execute(enderecoDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar dados de endereço: número inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para contato com bairro inválido")
        public void deveLancarExcecaoParaContatoComBairroInvalido() {
            String bairroInvalido = "";
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouro, numero, bairroInvalido, cidade, uf, cep);
            Assertions.assertThatThrownBy(() -> validaEndereco.execute(enderecoDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar dados de endereço: bairro inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para contato com cidade inválida")
        public void deveLancarExcecaoParaContatoComCidadeInvalida() {
            String cidadeInvalida = "";
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouro, numero, bairro, cidadeInvalida, uf, cep);
            Assertions.assertThatThrownBy(() -> validaEndereco.execute(enderecoDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar dados de endereço: cidade inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para contato com uf inválida")
        public void deveLancarExcecaoParaContatoComUfInvalida() {
            String ufInvalida = "";
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouro, numero, bairro, cidade, ufInvalida, cep);
            Assertions.assertThatThrownBy(() -> validaEndereco.execute(enderecoDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar dados de endereço: uf inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para contato com cep inválido")
        public void deveLancarExcecaoParaContatoComCepInvalido() {
            Long cepInvalido = 0L;
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouro, numero, bairro, cidade, uf, cepInvalido);
            Assertions.assertThatThrownBy(() -> validaEndereco.execute(enderecoDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar dados de endereço: cep inválido");
        }

        @Test
        @DisplayName("Deve retornar endereço corretamente")
        public void deveRetornarEnderecoCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, logradouro, numero, bairro, cidade, uf, cep);
            Endereco endereco = validaEndereco.execute(enderecoDTO);
            assertThat(endereco).isInstanceOf(Endereco.class);
            assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
            assertThat(endereco.getNumero()).isEqualTo(numero);
            assertThat(endereco.getBairro()).isEqualTo(bairro);
            assertThat(endereco.getCidade()).isEqualTo(cidade);
            assertThat(endereco.getUf()).isEqualTo(uf);
            assertThat(endereco.getCep()).isEqualTo(cep);
        }

    }

}
