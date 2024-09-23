package br.com.reserva.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ClienteTest {

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Cliente corretamente")
        public void deveInstanciarClienteCorretamente() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome valido", 1, 1L);
            assertThat(cliente).isInstanceOf(Cliente.class);
            assertThat(cliente.getEmail()).isEqualTo("teste@teste.com.br");
            assertThat(cliente.getNome()).isEqualTo("nome valido");
            assertThat(cliente.getDdd()).isEqualTo(1);
            assertThat(cliente.getTelefone()).isEqualTo(1L);
            assertThat(cliente.getReservas()).isEmpty();
        }

    }

    @Nested
    public class ValidacaoEmail {

        @Test
        @DisplayName("Deve lançar exceção para email nulo")
        public void deveLancarExcecaoParaEmailNulo() {
            String emailNulo = null;
            assertThatThrownBy(() -> new Cliente(emailNulo, "nome", 1, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("email inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para email em branco")
        public void deveLancarExcecaoParaemailEmBranco() {
            String emailEmBranco = "";
            assertThatThrownBy(() -> new Cliente(emailEmBranco, "nome", 1, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("email inválido");
        }

    }

    @Nested
    public class ValidacaoNome {

        @Test
        @DisplayName("Deve lançar exceção para nome nulo")
        public void deveLancarExcecaoParaNomeNulo() {
            String nomeNulo = null;
            assertThatThrownBy(() -> new Cliente("teste@teste.com.br", nomeNulo, 1, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para nome em branco")
        public void deveLancarExcecaoParaNomeEmBranco() {
            String nomeEmBranco = "";
            assertThatThrownBy(() -> new Cliente("teste@teste.com.br", nomeEmBranco, 1, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

    }

    @Nested
    public class ValidacaoDdd {

        @Test
        @DisplayName("Deve lançar exceção para ddd nulo")
        public void deveLancarExcecaoParaDddNulo() {
            Integer dddNulo = null;
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", dddNulo, 1L);
            assertThat(cliente.getDdd()).isNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para ddd zerado")
        public void deveLancarExcecaoParaDddZerado() {
            Integer dddZerado = 0;
            assertThatThrownBy(() -> new Cliente("teste@teste.com.br", "nome", dddZerado, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ddd inválido");
        }

    }

    @Nested
    public class ValidacaoTelefone {

        @Test
        @DisplayName("Deve lançar exceção para telefone nulo")
        public void deveLancarExcecaoParaTelefoneNulo() {
            Long telefoneNulo = null;
            Cliente cliente  = new Cliente("teste@teste.com.br", "nome", 1, telefoneNulo);
            assertThat(cliente.getTelefone()).isNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para telefone zerado")
        public void deveLancarExcecaoParaTelefoneZerado() {
            Long telefoneZerado = 0L;
            assertThatThrownBy(() -> new Cliente("teste@teste.com.br", "nome", 1, telefoneZerado))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("telefone inválido");
        }

    }

    @Nested
    public class ValidacaoReservas {

        @Test
        @DisplayName("Deve adicionar reserva")
        public void deveAdicionarReserva() {
            Reserva reserva = new Reserva();
            reserva.setId(1L);
            Cliente cliente = new Cliente();
            cliente.adicionaReserva(reserva);
            assertThat(cliente.getReservas()).isNotEmpty();
            assertThat(cliente.getReservas().get(0)).isEqualTo(reserva);
        }

    }

}
