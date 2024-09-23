package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.infrastructure.database.models.MesaModel;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class MesaRepositoryIT {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Nested
    public class ValidacaoMesaRepository {

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection mesa")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionCliente() {
            MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, null, null);
            MesaModel mesaCreated = mesaRepository.save(mesaModel);
            Optional<MesaModel> mesaRetrieved = mesaRepository.findById(mesaCreated.getId());
            Assertions.assertThat(mesaRetrieved.isPresent()).isTrue();
            Assertions.assertThat(mesaRetrieved.get()).isInstanceOf(MesaModel.class).isNotNull();
            Assertions.assertThat(mesaRetrieved.get().getId()).isNotNull();
            Assertions.assertThat(mesaRetrieved.get().getCapacidade()).isEqualTo(mesaModel.getCapacidade());
            Assertions.assertThat(mesaRetrieved.get().getStatus()).isEqualTo(mesaModel.getStatus());
        }

        @Test
        @DisplayName("Deve retornar lista de mesas por restaurante")
        public void deveRetornarListaDeMesasPorRestaurante() {
            RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restauranteCreated = restauranteRepository.save(restauranteModel);
            MesaModel mesaModel1 = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteCreated, null);
            MesaModel mesaModel2 = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteCreated, null);
            MesaModel mesaModel3 = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteCreated, null);
            mesaRepository.saveAll(List.of(mesaModel1, mesaModel2, mesaModel3));
            List<MesaModel> mesas = mesaRepository.findAllByRestauranteId(restauranteCreated.getId());
            Assertions.assertThat(mesas).isInstanceOf(List.class).isNotEmpty().hasSize(3);
        }

    }

}
