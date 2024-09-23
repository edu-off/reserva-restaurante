package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.RestauranteGateway;
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

public class BuscaRestaurantesPorNomeTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private BuscaRestaurantesPorNome buscaRestaurantesPorNome;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoBuscaRestaurantesPorNome {

        @Test
        @DisplayName("Deve retornar restaurantes corretamente")
        public void deveRetornarRestaurantesCorretamente() {
            Restaurante restaurante1 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Restaurante restaurante2 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            List<Restaurante> restaurantes = List.of(restaurante1, restaurante2);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Restaurante> pages = PageableExecutionUtils.getPage(restaurantes, pageable, restaurantes::size);
            when(restauranteGateway.buscaRestaurantesPorNome("nome", pageable)).thenReturn(pages);
            Page<Restaurante> pagesRetrieved = buscaRestaurantesPorNome.execute("nome", pageable);
            assertThat(pagesRetrieved).isInstanceOf(Page.class).isNotNull().hasSize(2);
        }

    }

}
