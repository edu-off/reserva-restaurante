package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.RestauranteModel;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
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
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class RestauranteRepositoryIT {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Nested
    public class ValidacaoRestauranteRepository {

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection restaurante")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionRestaurante() {
            RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restauranteCreated = restauranteRepository.save(restauranteModel);
            Optional<RestauranteModel> restauranteRetrieved = restauranteRepository.findById(restauranteCreated.getId());
            Assertions.assertThat(restauranteRetrieved.isPresent()).isTrue();
            Assertions.assertThat(restauranteRetrieved.get()).isInstanceOf(RestauranteModel.class).isNotNull();
            Assertions.assertThat(restauranteRetrieved.get().getId()).isNotNull();
            Assertions.assertThat(restauranteRetrieved.get().getNome()).isEqualTo(restauranteModel.getNome());
            Assertions.assertThat(restauranteRetrieved.get().getCulinaria()).isEqualTo(restauranteModel.getCulinaria());
            Assertions.assertThat(restauranteRetrieved.get().getCapacidade()).isEqualTo(restauranteModel.getCapacidade());
            Assertions.assertThat(restauranteRetrieved.get().getHorarioAbertura()).isInstanceOf(LocalTime.class).isNotNull();
            Assertions.assertThat(restauranteRetrieved.get().getHorarioEncerramento()).isInstanceOf(LocalTime.class).isNotNull();
            Assertions.assertThat(restauranteRetrieved.get().getEndereco()).isNull();
            Assertions.assertThat(restauranteRetrieved.get().getMesas()).isNull();
            Assertions.assertThat(restauranteRetrieved.get().getReservas()).isNull();
        }

    }

    @Nested
    public class ValidacaoBuscasRepository {

        @BeforeEach
        public void setup() {
            restauranteRepository.deleteAll();
            saveRestaurantes();
        }

        @Test
        @DisplayName("Deve incluir para restaurantes para efetuar a busca por nome corretamente")
        public void deveIncluirParaRestaurantesParaEfetuarABuscaPorNomeCorretamente() {
            Page<RestauranteModel> models = restauranteRepository.findByNomeContainingIgnoreCase("restaurante", PageRequest.of(0, 10));
            Assertions.assertThat(models).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve incluir para restaurantes para efetuar a busca por culinaria corretamente")
        public void deveIncluirParaRestaurantesParaEfetuarABuscaPorCulinariaCorretamente() {
            Page<RestauranteModel> models = restauranteRepository.findByCulinariaContainingIgnoreCase("arabe", PageRequest.of(0, 10));
            Assertions.assertThat(models).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        private void saveRestaurantes() {
            RestauranteModel restaurante1 = new RestauranteModel(null, "restaurante um", "culinaria italiana", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restaurante2 = new RestauranteModel(null, "restaurante dois", "culinaria arabe", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restaurante3 = new RestauranteModel(null, "teste", "culinaria arabe", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restaurante4 = new RestauranteModel(null, "aaaaa arabe", "culinaria japonesa", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restaurante5 = new RestauranteModel(null, "bbbbb", "culinaria chinesa", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            List<RestauranteModel> restaurantes = List.of(restaurante1, restaurante2, restaurante3, restaurante4, restaurante5);
            restauranteRepository.saveAll(restaurantes);
        }

    }

}
