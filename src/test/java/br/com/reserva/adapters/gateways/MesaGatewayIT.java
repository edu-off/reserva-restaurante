package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.objectsvalue.Endereco;
import br.com.reserva.infrastructure.database.repositories.MesaRepository;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class MesaGatewayIT {

    @Autowired
    private MesaGateway mesaGateway;

    @Autowired
    private RestauranteGateway restauranteGateway;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setup() {
        mesaRepository.deleteAll();
        restauranteRepository.deleteAll();
    }

    @Nested
    public class ValidacaoMesaGateway {

        @Test
        @DisplayName("Deve buscar mesa corretamente pelo id")
        public void deveBuscarMesaCorretamentePeloId() {
            Mesa mesaCreated1 = mesaGateway.salvaMesa(new Mesa(1, StatusMesa.DISPONIVEL));
            Mesa mesaCreated2 = mesaGateway.salvaMesa(new Mesa(2, StatusMesa.RESERVADA));
            Mesa mesaRetrieved = mesaGateway.buscaMesaPorId(mesaCreated1.getId());
            assertThat(mesaRetrieved).isInstanceOf(Mesa.class).isNotNull();
            assertThat(mesaRetrieved.getId()).isNotNull().isNotZero().isNotEqualTo(mesaCreated2.getId());
            assertThat(mesaRetrieved.getCapacidade()).isEqualTo(mesaCreated1.getCapacidade()).isNotEqualTo(mesaCreated2.getCapacidade());
            assertThat(mesaRetrieved.getStatus()).isEqualTo(mesaCreated1.getStatus()).isNotEqualTo(mesaCreated2.getStatus());
        }

        @Test
        @DisplayName("Deve buscar mesa por id que nao existe")
        public void deveBuscarMesaPorIdQueNaoExiste() {
            Mesa mesaRetrieved = mesaGateway.buscaMesaPorId(1L);
            assertThat(mesaRetrieved).isNull();
        }

        @Test
        @DisplayName("Deve buscar mesas corretamente pelo id do restaurante")
        public void deveBuscarMesasCorretamentePeloIdDoRestaurante() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            Mesa mesa1 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa2 = new Mesa(2, StatusMesa.RESERVADA);
            mesa1.setRestaurante(restauranteCreated);
            mesa2.setRestaurante(restauranteCreated);
            Mesa mesaCreated1 = mesaGateway.salvaMesa(mesa1);
            Mesa mesaCreated2 = mesaGateway.salvaMesa(mesa2);
            List<Mesa> mesas = mesaGateway.buscaMesasPorRestaurante(restauranteCreated.getId());
            assertThat(mesas).isInstanceOf(List.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve salvar dominio mesa")
        public void deveSalvarDominioMesa() {
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesaCreated = mesaGateway.salvaMesa(mesa);
            assertThat(mesaCreated).isInstanceOf(Mesa.class).isNotNull();
            assertThat(mesaCreated.getId()).isNotNull().isNotZero();
            assertThat(mesaCreated.getCapacidade()).isEqualTo(mesa.getCapacidade());
            assertThat(mesaCreated.getStatus()).isEqualTo(mesa.getStatus());
            assertThat(mesaCreated.getRestaurante()).isNull();
        }

        @Test
        @DisplayName("Deve atualizar dominio mesa")
        public void deveAtualizarDominioMesa() {
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesaCreated = mesaGateway.salvaMesa(mesa);
            mesaCreated.setStatus(StatusMesa.RESERVADA);
            Mesa mesaUpdated = mesaGateway.atualizaMesa(mesaCreated.getId(), mesaCreated);
            assertThat(mesaUpdated).isInstanceOf(Mesa.class).isNotNull();
            assertThat(mesaUpdated.getId()).isEqualTo(mesaCreated.getId());
            assertThat(mesaUpdated.getCapacidade()).isEqualTo(mesaCreated.getCapacidade());
            assertThat(mesaUpdated.getStatus()).isNotEqualTo(mesa.getStatus());
        }

        @Test
        @DisplayName("Deve atualizar dominio mesa inexistente")
        public void deveAtualizarDominioMesaInexistente() {
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Mesa mesaUpdated = mesaGateway.atualizaMesa(mesa.getId(), mesa);
            assertThat(mesaUpdated).isNull();
        }

    }

}
