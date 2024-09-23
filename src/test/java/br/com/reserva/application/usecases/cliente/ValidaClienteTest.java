package br.com.reserva.application.usecases.cliente;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.domain.entities.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidaClienteTest {

    private final ValidaCliente validaCliente = new ValidaCliente();


    @Nested
    public class ValidacaoTransformacaoDtoParaDominio {

        @Test
        @DisplayName("Deve lançar exceção para cliente com algum dados invalido")
        public void deveLancarExcecaoParaClienteComAlgumDadoInvalido() {
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", 1, 1L);
            Assertions.assertThatThrownBy(() -> validaCliente.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("erro ao validar dados do cliente: email inválido");
        }

        @Test
        @DisplayName("Deve retornar cliente corretamente")
        public void deveRetornarClienteCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            Cliente cliente = validaCliente.execute(clienteDTO);
            assertThat(cliente).isInstanceOf(Cliente.class).isNotNull();
            assertThat(cliente.getEmail()).isEqualTo("teste@teste.com.br");
            assertThat(cliente.getNome()).isEqualTo("nome");
            assertThat(cliente.getDdd()).isEqualTo(1);
            assertThat(cliente.getTelefone()).isEqualTo(1L);
            assertThat(cliente.getReservas()).isInstanceOf(List.class).isEmpty();
        }

    }

}
