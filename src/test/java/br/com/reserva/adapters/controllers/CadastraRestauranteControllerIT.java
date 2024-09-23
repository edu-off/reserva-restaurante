package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.exceptions.EnderecoException;
import br.com.reserva.application.exceptions.RestauranteException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class CadastraRestauranteControllerIT {

    @Autowired
    private CadastraRestauranteController cadastraRestauranteController;

    @Nested
    public class CriacaoRestaurante {

        @Test
        @DisplayName("Deve lancar excecao na validacao de endereco")
        public void deveLancarExcecaoNaValidacaoDeEndereco() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "uf", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, "nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, enderecoDTO);
            assertThatThrownBy(() -> cadastraRestauranteController.execute(restauranteDTO))
                    .isInstanceOf(EnderecoException.class).hasMessage("erro ao validar dados de endereço: uf inválida");
        }

        @Test
        @DisplayName("Deve lancar excecao na validacao de restaurante")
        public void deveLancarExcecaoNaValidacaoDeRestaurante() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, "", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, enderecoDTO);

            assertThatThrownBy(() -> cadastraRestauranteController.execute(restauranteDTO))
                    .isInstanceOf(RestauranteException.class).hasMessage("erro ao validar dados do restaurante: nome inválido");
        }

        @Test
        @DisplayName("Deve criar restaurante corretamente")
        public void deveCriarRestauranteCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, enderecoDTO);

            RestauranteDTO restauranteDTOCreated = cadastraRestauranteController.execute(restauranteDTO);
            assertThat(restauranteDTOCreated).isInstanceOf(RestauranteDTO.class).isNotNull();
            assertThat(restauranteDTOCreated.getId()).isNotNull().isNotZero();
        }

    }

}
