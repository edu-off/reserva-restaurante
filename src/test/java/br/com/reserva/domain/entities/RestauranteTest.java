package br.com.reserva.domain.entities;

import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RestauranteTest {

    private final String nome = "nome valido";
    private final String culinaria = "culinaria valida";
    private final Integer capacidade = 100;
    private final LocalTime horarioAbertura = LocalTime.parse("12:00");
    private final LocalTime horarioEncerramento = LocalTime.parse("23:59");
    private final Integer ddd = 1;
    private final Long telefone = 1L;
    private final Endereco endereco = new Endereco();

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Restaurante corretamente")
        public void deveInstanciarRestauranteCorretamente() {
            Restaurante restaurante = new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco);
            assertThat(restaurante).isInstanceOf(Restaurante.class);
            assertThat(restaurante.getId()).isNull();
            assertThat(restaurante.getNome()).isEqualTo(nome);
            assertThat(restaurante.getCulinaria()).isEqualTo(culinaria);
            assertThat(restaurante.getHorarioAbertura()).isInstanceOf(LocalTime.class).isEqualTo(horarioAbertura);
            assertThat(restaurante.getHorarioEncerramento()).isInstanceOf(LocalTime.class).isEqualTo(horarioEncerramento);
            assertThat(restaurante.getDdd()).isEqualTo(ddd);
            assertThat(restaurante.getTelefone()).isEqualTo(telefone);
            assertThat(restaurante.getEndereco()).isInstanceOf(Endereco.class);
            assertThat(restaurante.getMesas()).isInstanceOf(List.class).isEmpty();
            assertThat(restaurante.getReservas()).isInstanceOf(List.class).isEmpty();
        }

    }

    @Nested
    public class ValidacaoNome {

        @Test
        @DisplayName("Deve lançar exceção para nome nulo")
        public void deveLancarExcecaoParaNomeNulo() {
            String nomeNulo = null;
            assertThatThrownBy(() -> new Restaurante(nomeNulo, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para nome em branco")
        public void deveLancarExcecaoParaNomeEmBranco() {
            String nomeEmBranco = "";
            assertThatThrownBy(() -> new Restaurante(nomeEmBranco, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

    }


    @Nested
    public class ValidacaoCulinaria {

        @Test
        @DisplayName("Deve lançar exceção para culinaria nula")
        public void deveLancarExcecaoParaCulinariaNula() {
            String culinariaNulo = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinariaNulo, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("culinária inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para culinaria em branco")
        public void deveLancarExcecaoParaCulinariaEmBranco() {
            String culinariaEmBranco = "";
            assertThatThrownBy(() -> new Restaurante(nome, culinariaEmBranco, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("culinária inválida");
        }

    }

    @Nested
    public class ValidacaoCapacidade {

        @Test
        @DisplayName("Deve lançar exceção para capacidade nula")
        public void deveLancarExcecaoParaCapacidadeNula() {
            Integer capacidadeNula = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidadeNula, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("capacidade inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para capacidade inferior ao minimo permitido")
        public void deveLancarExcecaoParaCapacidadeInferiorAoMinimoPermitido() {
            Integer capacidadeMenorQueOMinimoPermitido = 0;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidadeMenorQueOMinimoPermitido, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("capacidade inferior ao permitido");
        }

        @Test
        @DisplayName("Deve lançar exceção para capacidade superior ao máximo permitido")
        public void deveLancarExcecaoParaCapacidadeSuperiorAoMaximoPermitido() {
            Integer capacidadeMaiorQueOMaximoPermitido = 1001;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidadeMaiorQueOMaximoPermitido, horarioAbertura, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("capacidade superior ao permitido");
        }

    }

    @Nested
    public class ValidacaoHorarioFuncionamento {

        @Test
        @DisplayName("Deve lançar exceção para horario de abertura nulo")
        public void deveLancarExcecaoParaHorarioDeAberturaNulo() {
            LocalTime horarioAberturaNulo = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidade, horarioAberturaNulo, horarioEncerramento, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("horario de funcionamento inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para horario de encerramento nulo")
        public void deveLancarExcecaoParaHorarioDeEncerramentoNulo() {
            LocalTime horarioEncerramentoNulo = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramentoNulo, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("horario de funcionamento inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para horario de abertura superior ao horario de encerramento")
        public void deveLancarExcecaoParaHorarioDeAberturaSuperiorAoHorarioDeEncerramento() {
            LocalTime horarioAberturaSuperior = LocalTime.parse("23:00");
            LocalTime horarioEncerramentoInferior = LocalTime.parse("22:00");
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidade, horarioAberturaSuperior, horarioEncerramentoInferior, ddd, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("horario de abertura não deve ser superior ao horário de encerramento");
        }

    }





    @Nested
    public class ValidacaoDdd {

        @Test
        @DisplayName("Deve lançar exceção para ddd nulo")
        public void deveLancarExcecaoParaDddNulo() {
            Integer dddNulo = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramento, dddNulo, telefone, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ddd inválido");
        }

    }

    @Nested
    public class ValidacaoTelefone {

        @Test
        @DisplayName("Deve lançar exceção para endereço nulo")
        public void deveLancarExcecaoParaEnderecoNulo() {
            Long telefoneNulo = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefoneNulo, endereco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("telefone inválido");
        }

    }






    @Nested
    public class ValidacaoEndereco {

        @Test
        @DisplayName("Deve lançar exceção para endereço nulo")
        public void deveLancarExcecaoParaEnderecoNulo() {
            Endereco enderecoNulo = null;
            assertThatThrownBy(() -> new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, enderecoNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("endereço inválido");
        }

    }

    @Nested
    public class ValidacaoMesa {

        @Test
        @DisplayName("Deve adicionar uma mesa corretamente")
        public void deveAdicionarUmaMesaCorretamente() {
            UUID id = UUID.randomUUID();
            Mesa mesa = new Mesa();
            List<Mesa> mesas = List.of(mesa);
            Restaurante restaurante = new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco);
            restaurante.setMesas(mesas);
            assertThat(restaurante.getMesas()).isInstanceOf(List.class).isNotEmpty();
            assertThat(restaurante.getMesas().get(0)).isEqualTo(mesa);
        }

    }

    @Nested
    public class ValidacaoReserva {

        @Test
        @DisplayName("Deve adicionar uma reserva corretamente")
        public void deveAdicionarUmaReservaCorretamente() {
            Reserva reserva = new Reserva();
            reserva.setId(1L);
            Restaurante restaurante = new Restaurante(nome, culinaria, capacidade, horarioAbertura, horarioEncerramento, ddd, telefone, endereco);
            restaurante.adicionaReserva(reserva);
            assertThat(restaurante.getReservas()).isInstanceOf(List.class).isNotEmpty();
            assertThat(restaurante.getReservas().get(0)).isEqualTo(reserva);
            assertThat(restaurante.getReservas().get(0).getId()).isEqualTo(1L);
        }

    }


}
