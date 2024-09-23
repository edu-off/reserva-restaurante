package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.objectsvalue.Endereco;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class RestauranteGatewayIT {

    @Autowired
    private RestauranteGateway restauranteGateway;

    @Nested
    public class ValidacaoRestauranteGateway {

        @Test
        @DisplayName("Deve salvar restaurante")
        public void deveSalvarRestaurante() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            assertThat(restauranteCreated).isInstanceOf(Restaurante.class).isNotNull();
            assertThat(restauranteCreated.getId()).isNotNull().isNotZero();
            assertThat(restauranteCreated.getNome()).isEqualTo(restaurante.getNome());
            assertThat(restauranteCreated.getCulinaria()).isEqualTo(restaurante.getCulinaria());
            assertThat(restauranteCreated.getCapacidade()).isEqualTo(restaurante.getCapacidade());
            assertThat(restauranteCreated.getHorarioAbertura()).isEqualTo(restaurante.getHorarioAbertura());
            assertThat(restauranteCreated.getHorarioEncerramento()).isEqualTo(restaurante.getHorarioEncerramento());
            assertThat(restauranteCreated.getDdd()).isEqualTo(restaurante.getDdd());
            assertThat(restauranteCreated.getTelefone()).isEqualTo(restaurante.getTelefone());
            assertThat(restauranteCreated.getEndereco()).isNull();
            assertThat(restauranteCreated.getMesas()).isEmpty();
            assertThat(restauranteCreated.getReservas()).isEmpty();
        }

        @Test
        @DisplayName("Deve atualizar restaurante para id existente")
        public void deveAtualizarRestauranteParaIdExistente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            restauranteCreated.setNome("restaurante");
            Restaurante restauranteUpdated = restauranteGateway.atualizaRestaurante(restauranteCreated.getId(), restauranteCreated);
            assertThat(restauranteUpdated).isInstanceOf(Restaurante.class).isNotNull();
            assertThat(restauranteUpdated.getNome()).isNotEqualTo(restaurante.getNome()).isEqualTo("restaurante");
        }

        @Test
        @DisplayName("Deve atualizar restaurante para id inexistente")
        public void deveAtualizarRestauranteParaIdInexistente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Restaurante restauranteUpdated = restauranteGateway.atualizaRestaurante(0L, restaurante);
            assertThat(restauranteUpdated).isNull();
        }

    }

    @Nested
    public class ValidacaoBuscasRestauranteGateway {

        @Autowired
        private RestauranteRepository restauranteRepository;

        @BeforeEach
        public void setup() {
            restauranteRepository.deleteAll();
            salvaRegistros();
        }

        @Test
        @DisplayName("Deve buscar restaurante por id")
        public void deveBuscarRestaurantePorId() {
            Restaurante restaurante = new Restaurante("restaurante", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            Restaurante restauranteRetrieved = restauranteGateway.buscaRestaurantePorId(restauranteCreated.getId());
            assertThat(restauranteRetrieved).isInstanceOf(Restaurante.class).isNotNull();
            assertThat(restauranteRetrieved.getId()).isNotNull().isNotZero();
            assertThat(restauranteRetrieved.getNome()).isEqualTo(restauranteCreated.getNome());
            assertThat(restauranteRetrieved.getCulinaria()).isEqualTo(restauranteCreated.getCulinaria());
            assertThat(restauranteRetrieved.getCapacidade()).isEqualTo(restauranteCreated.getCapacidade());
            assertThat(restauranteRetrieved.getHorarioAbertura()).isEqualTo(restauranteCreated.getHorarioAbertura());
            assertThat(restauranteRetrieved.getHorarioEncerramento()).isEqualTo(restauranteCreated.getHorarioEncerramento());
            assertThat(restauranteRetrieved.getDdd()).isEqualTo(restauranteCreated.getDdd());
            assertThat(restauranteRetrieved.getTelefone()).isEqualTo(restauranteCreated.getTelefone());
            assertThat(restauranteRetrieved.getEndereco()).isNull();
            assertThat(restauranteRetrieved.getMesas()).isEmpty();
            assertThat(restauranteRetrieved.getReservas()).isEmpty();
        }

        @Test
        @DisplayName("Deve buscar restaurantes por nome")
        public void deveBuscarRestaurantesPorNome() {
            Page<Restaurante> restaurantes = restauranteGateway.buscaRestaurantesPorNome("restaurante", PageRequest.of(0, 10));
            assertThat(restaurantes).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar restaurantes por culinaria")
        public void deveBuscarRestaurantesPorCulinaria() {
            Page<Restaurante> restaurantes = restauranteGateway.buscaRestaurantesPorCulinaria("arabe", PageRequest.of(0, 10));
            assertThat(restaurantes).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        private void salvaRegistros() {
            Restaurante restaurante1 = new Restaurante("restaurante 1", "culinaria italiana", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Restaurante restaurante2 = new Restaurante("restaurante 2", "culinaria arabe", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Restaurante restaurante3 = new Restaurante("teste", "culinaria arabe", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Restaurante restaurante4 = new Restaurante("aaaaaaa", "culinaria japonesa", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante1.setEndereco(null);
            restaurante2.setEndereco(null);
            restaurante3.setEndereco(null);
            restaurante4.setEndereco(null);
            restauranteGateway.salvaRestaurante(restaurante1);
            restauranteGateway.salvaRestaurante(restaurante2);
            restauranteGateway.salvaRestaurante(restaurante3);
            restauranteGateway.salvaRestaurante(restaurante4);
        }

    }

}
