package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.BuscaRestaurantesPresenter;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.usecases.restaurante.BuscaRestaurantesPorCulinaria;
import br.com.reserva.application.usecases.restaurante.BuscaRestaurantesPorNome;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaRestaurantesControllerTest {

    @Mock
    private BuscaRestaurantesPorNome buscaRestaurantesPorNome;

    @Mock
    private BuscaRestaurantesPorCulinaria buscaRestaurantesPorCulinaria;

    @Mock
    private BuscaRestaurantesPresenter presenter;

    @InjectMocks
    private BuscaRestaurantesController buscaRestaurantesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoDeBuscas {

        @Test
        @DisplayName("Deve buscar reservas por cliente")
        public void deveBuscarReservasPorCliente() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Restaurante> pageRestauranteMocked = mockPageRestaurantes();
            Page<RestauranteDTO> pageRestaurantesDTOMocked = mockPageRestaurantesDTO();
            when(buscaRestaurantesPorNome.execute("nome", pageable)).thenReturn(pageRestauranteMocked);
            when(presenter.execute(pageRestauranteMocked, pageable)).thenReturn(pageRestaurantesDTOMocked);
            Page<RestauranteDTO> pageRestaurantesDTOReturned = buscaRestaurantesController.porNome("nome", pageable);
            assertThat(pageRestaurantesDTOReturned).isInstanceOf(Page.class).isNotNull().isEqualTo(pageRestaurantesDTOMocked);
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante")
        public void deveBuscarReservasPorRestaurante() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Restaurante> pageRestauranteMocked = mockPageRestaurantes();
            Page<RestauranteDTO> pageRestaurantesDTOMocked = mockPageRestaurantesDTO();
            when(buscaRestaurantesPorCulinaria.execute("culinaria", pageable)).thenReturn(pageRestauranteMocked);
            when(presenter.execute(pageRestauranteMocked, pageable)).thenReturn(pageRestaurantesDTOMocked);
            Page<RestauranteDTO> pageRestaurantesDTOReturned = buscaRestaurantesController.porCulinaria("culinaria", pageable);
            assertThat(pageRestaurantesDTOReturned).isInstanceOf(Page.class).isNotNull().isEqualTo(pageRestaurantesDTOMocked);
        }

        private Page<Restaurante> mockPageRestaurantes() {
            Pageable pageable = PageRequest.of(0, 10);
            Endereco endereco = new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Restaurante restaurante1 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            Restaurante restaurante2 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            Restaurante restaurante3 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            Restaurante restaurante4 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            List<Restaurante> restaurantes = List.of(restaurante1, restaurante2, restaurante3, restaurante4);
            return PageableExecutionUtils.getPage(restaurantes, pageable, restaurantes::size);
        }

        private Page<RestauranteDTO> mockPageRestaurantesDTO() {
            Pageable pageable = PageRequest.of(0, 10);
            RestauranteDTO restaurante1 = new RestauranteDTO(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null);
            RestauranteDTO restaurante2 = new RestauranteDTO(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null);
            RestauranteDTO restaurante3 = new RestauranteDTO(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null);
            RestauranteDTO restaurante4 = new RestauranteDTO(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null);
            List<RestauranteDTO> restaurantes = List.of(restaurante1, restaurante2, restaurante3, restaurante4);
            return PageableExecutionUtils.getPage(restaurantes, pageable, restaurantes::size);
        }


    }

}
