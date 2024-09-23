package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.CadastraRestaurantePresenter;
import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.exceptions.EnderecoException;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.application.usecases.restaurante.SalvaEndereco;
import br.com.reserva.application.usecases.restaurante.SalvaRestaurante;
import br.com.reserva.application.usecases.restaurante.ValidaEndereco;
import br.com.reserva.application.usecases.restaurante.ValidaRestaurante;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CadastraRestauranteControllerTest {

    @Mock
    private ValidaEndereco validaEndereco;

    @Mock
    private ValidaRestaurante validaRestaurante;

    @Mock
    private SalvaRestaurante salvaRestaurante;

    @Mock
    private SalvaEndereco salvaEndereco;

    @Mock
    private CadastraRestaurantePresenter cadastraRestaurantePresenter;

    @InjectMocks
    private CadastraRestauranteController cadastraRestauranteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class CriacaoRestaurante {

        @Test
        @DisplayName("Deve lancar excecao na validacao de endereco")
        public void deveLancarExcecaoNaValidacaoDeEndereco() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, "nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, enderecoDTO);
            Mockito.when(validaEndereco.execute(restauranteDTO.getEndereco())).thenThrow(EnderecoException.class);
            assertThatThrownBy(() -> cadastraRestauranteController.execute(restauranteDTO))
                    .isInstanceOf(EnderecoException.class);
        }

        @Test
        @DisplayName("Deve lancar excecao na validacao de restaurante")
        public void deveLancarExcecaoNaValidacaoDeRestaurante() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "uf", 1L);
            Endereco endereco = new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, "nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, enderecoDTO);
            Mockito.when(validaEndereco.execute(restauranteDTO.getEndereco())).thenReturn(endereco);
            Mockito.when(validaRestaurante.execute(restauranteDTO, endereco)).thenThrow(RestauranteException.class);
            assertThatThrownBy(() -> cadastraRestauranteController.execute(restauranteDTO))
                    .isInstanceOf(RestauranteException.class);
        }

        @Test
        @DisplayName("Deve criar restaurante corretamente")
        public void deveCriarRestauranteCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "logradouro", "numero", "bairro", "cidade", "uf", 1L);
            Endereco endereco = new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            endereco.setId(1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, enderecoDTO);
            Restaurante restaurante = new Restaurante("nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            restaurante.setId(1L);
            Mockito.when(validaEndereco.execute(restauranteDTO.getEndereco())).thenReturn(endereco);
            Mockito.when(validaRestaurante.execute(restauranteDTO, endereco)).thenReturn(restaurante);
            Mockito.when(salvaEndereco.execute(endereco)).thenReturn(endereco);
            Mockito.when(salvaRestaurante.execute(restaurante)).thenReturn(restaurante);
            Mockito.when(cadastraRestaurantePresenter.execute(restaurante, endereco)).thenReturn(restauranteDTO);
            RestauranteDTO restauranteDTOCreated = cadastraRestauranteController.execute(restauranteDTO);
            assertThat(restauranteDTOCreated).isInstanceOf(RestauranteDTO.class).isNotNull();
            assertThat(restauranteDTOCreated.getId()).isNotNull().isNotZero();

        }

    }

}
