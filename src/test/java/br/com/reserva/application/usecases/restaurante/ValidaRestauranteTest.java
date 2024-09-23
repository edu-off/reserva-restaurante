package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidaRestauranteTest {

    private final ValidaRestaurante validaRestaurante = new ValidaRestaurante();
    private final String nome = "nome valido";
    private final String culinaria = "culinaria valida";
    private final Integer capacidade = 100;
    private final LocalTime horarioAbertura = LocalTime.parse("12:00");
    private final LocalTime horarioEncerramento = LocalTime.parse("23:59");
    private final EnderecoDTO enderecoDTO = new EnderecoDTO();
    private final Endereco endereco = new Endereco();

    @Nested
    public class ValidacaoTransformacaoDtoParaDominio {

        @Test
        @DisplayName("Deve lançar exceção para restaurante com nome inválido")
        public void deveLancarExcecaoParaRestauranteComNomeInvalido() {
            String nomeInvalido = "";
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nomeInvalido, culinaria,
                    capacidade, horarioAbertura, horarioEncerramento, 1, 1L, enderecoDTO);
            Assertions.assertThatThrownBy(() -> validaRestaurante.execute(restauranteDTO, endereco))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("erro ao validar dados do restaurante: nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante com culinária inválida")
        public void deveLancarExcecaoParaRestauranteComCulinariaInvalida() {
            String culinariaInvalida = "";
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nome, culinariaInvalida,
                    capacidade, horarioAbertura, horarioEncerramento, 1, 1L, enderecoDTO);
            Assertions.assertThatThrownBy(() -> validaRestaurante.execute(restauranteDTO, endereco))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("erro ao validar dados do restaurante: culinária inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante com capacidade inválida")
        public void deveLancarExcecaoParaRestauranteComCapacidadeInvalida() {
            Integer capacidadeInvalida = 0;
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nome, culinaria,
                    capacidadeInvalida, horarioAbertura, horarioEncerramento, 1, 1L, enderecoDTO);
            Assertions.assertThatThrownBy(() -> validaRestaurante.execute(restauranteDTO, endereco))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("erro ao validar dados do restaurante: capacidade inferior ao permitido");
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante com horario de abertura inválido")
        public void deveLancarExcecaoParaRestauranteComHorarioAberturaInvalido() {
            LocalTime horarioAberturaInvalido = null;
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nome, culinaria,
                    capacidade, horarioAberturaInvalido, horarioEncerramento, 1, 1L, enderecoDTO);
            Assertions.assertThatThrownBy(() -> validaRestaurante.execute(restauranteDTO, endereco))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("erro ao validar dados do restaurante: horario de funcionamento inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante com horario de encerramento inválido")
        public void deveLancarExcecaoParaRestauranteComHorarioEncerramentoInvalido() {
            LocalTime horarioEncerramentoInvalido = null;
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nome, culinaria,
                    capacidade, horarioAbertura, horarioEncerramentoInvalido, 1, 1L, enderecoDTO);
            Assertions.assertThatThrownBy(() -> validaRestaurante.execute(restauranteDTO, endereco))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("erro ao validar dados do restaurante: horario de funcionamento inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante com endereco inválido")
        public void deveLancarExcecaoParaRestauranteComEnderecoInvalido() {
            Endereco enderecoInvalido = null;
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nome, culinaria,
                    capacidade, horarioAbertura, horarioEncerramento, 1, 1L, enderecoDTO);
            Assertions.assertThatThrownBy(() -> validaRestaurante.execute(restauranteDTO, enderecoInvalido))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("erro ao validar dados do restaurante: endereço inválido");
        }

        @Test
        @DisplayName("Deve retornar restaurante corretamente")
        public void deveRetornarRestauranteCorretamente() {
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, nome, culinaria,
                    capacidade, horarioAbertura, horarioEncerramento, 1, 1L, enderecoDTO);
            Restaurante restaurante = validaRestaurante.execute(restauranteDTO, endereco);
            assertThat(restaurante).isInstanceOf(Restaurante.class);
            assertThat(restaurante).isInstanceOf(Restaurante.class);
            assertThat(restaurante.getNome()).isEqualTo(nome);
            assertThat(restaurante.getCulinaria()).isEqualTo(culinaria);
            assertThat(restaurante.getCapacidade()).isEqualTo(capacidade);
            assertThat(restaurante.getHorarioAbertura()).isEqualTo(horarioAbertura);
            assertThat(restaurante.getHorarioEncerramento()).isEqualTo(horarioEncerramento);
            assertThat(restaurante.getEndereco()).isInstanceOf(Endereco.class);
            assertThat(restaurante.getEndereco()).isEqualTo(endereco);
        }

    }

}
