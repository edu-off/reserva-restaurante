package br.com.reserva.adapters.resources;

import br.com.reserva.application.dto.*;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.infrastructure.database.models.ClienteModel;
import br.com.reserva.infrastructure.database.models.MesaModel;
import br.com.reserva.infrastructure.database.models.ReservaModel;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import br.com.reserva.infrastructure.database.repositories.MesaRepository;
import br.com.reserva.infrastructure.database.repositories.ReservaRepository;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservaResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private ClienteModel clienteModelCreated;
    private RestauranteModel restauranteModelCreated;
    private MesaModel mesaModelCreated;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ClienteModel clienteModel = new ClienteModel("teste1@teste.com.br", "nome", 1, 1L, null);
        clienteModelCreated = clienteRepository.save(clienteModel);
        LocalTime abertura = LocalTime.parse("12:00");
        LocalTime encerramento = LocalTime.parse("23:00");
        RestauranteModel restauranteModel = new RestauranteModel(null, "restaurante 1", "culinaria japonesa", 1, abertura, encerramento, 1, 1L, null, null, null);
        restauranteModelCreated = restauranteRepository.save(restauranteModel);
        MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteModelCreated, null);
        mesaModelCreated = mesaRepository.save(mesaModel);
    }

    @Nested
    public class ValidaBuscas {

        @BeforeEach
        public void setup() {
            ReservaModel reservaModel1 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-08-01 21:00", formatter), 1, restauranteModelCreated, clienteModelCreated, mesaModelCreated, null);
            ReservaModel reservaModel2 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-08-01 21:00", formatter), 1, restauranteModelCreated, clienteModelCreated, mesaModelCreated, null);
            ReservaModel reservaModel3 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-09-01 21:00", formatter), 1, restauranteModelCreated, clienteModelCreated, mesaModelCreated, null);
            ReservaModel reservaModel4 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-09-01 21:00", formatter), 1, restauranteModelCreated, clienteModelCreated, mesaModelCreated, null);
            reservaRepository.saveAll(List.of(reservaModel1, reservaModel2, reservaModel3, reservaModel4));
        }

        @Test
        @DisplayName("Deve retornar reservas por cliente")
        public void deveRetornarReservasPorCliente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/reserva/busca-cliente/{clienteEmail}", restauranteModelCreated.getId())
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ListaReservaResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar reservas por restaurante")
        public void deveRetornarReservasPorRestaurante() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/reserva/busca-restaurante/{restauranteId}", restauranteModelCreated.getId())
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ListaReservaResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar reservas por cliente periodo")
        public void deveRetornarReservasPorClientePeriodo() {
            LocalDateTime inicio = LocalDateTime.parse("2024-08-01 20:00", formatter);
            LocalDateTime fim = LocalDateTime.parse("2024-08-01 22:00", formatter);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().param("inicio", inicio.toString()).param("fim", fim.toString())
                    .get("/reserva/busca-cliente-periodo/{clienteEmail}", restauranteModelCreated.getId())
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ListaReservaResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar reservas por restaurante periodo")
        public void deveRetornarReservasPorRestaurantePeriodo() {
            LocalDateTime inicio = LocalDateTime.parse("2024-08-01 20:00", formatter);
            LocalDateTime fim = LocalDateTime.parse("2024-08-01 22:00", formatter);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().param("inicio", inicio.toString()).param("fim", fim.toString())
                    .get("/reserva/busca-restaurante-periodo/{restauranteId}", restauranteModelCreated.getId())
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ListaReservaResponseSchema.json"));
        }

    }

    @Nested
    public class ValidaReserva {

        private ReservaModel reservaModelCreated;

        @BeforeEach
        public void setup() {
            ReservaModel reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-08-01 21:00", formatter), 1, restauranteModelCreated, clienteModelCreated, mesaModelCreated, null);
            reservaModelCreated = reservaRepository.save(reservaModel);
        }

        @Test
        @DisplayName("Deve retornar bad request para reserva invalida")
        public void deveRetornarBadRequestParaReservaInvalida() {
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModelCreated.getId(), "nome", "culinaria", 1, restauranteModelCreated.getHorarioAbertura(), restauranteModelCreated.getHorarioEncerramento(), 1, 1L, null);
            ClienteDTO clienteDTO = new ClienteDTO(clienteModelCreated.getEmail(), "nome", 1, 1L);
            ReservaDTO reservaDTO = new ReservaDTO(null, null, LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(reservaDTO)
                    .when().post("/reserva")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve criar reserva")
        public void deveCriarReserva() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModelCreated.getId(), "nome", "culinaria", 1, restauranteModelCreated.getHorarioAbertura(), restauranteModelCreated.getHorarioEncerramento(), 1, 1L, enderecoDTO);
            ClienteDTO clienteDTO = new ClienteDTO(clienteModelCreated.getEmail(), "nome", 1, 1L);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(reservaDTO)
                    .when().post("/reserva")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body("$", hasKey("id")).body("id", notNullValue())
                    .body("$", hasKey("status")).body("status", equalTo(reservaDTO.getStatus()))
                    .body("$", hasKey("agendamento")).body("agendamento", notNullValue())
                    .body("$", hasKey("quantidadePessoas")).body("quantidadePessoas", equalTo(reservaDTO.getQuantidadePessoas()))
                    .body("$", hasKey("restaurante")).body("restaurante", notNullValue())
                    .body("$", hasKey("cliente")).body("cliente", notNullValue())
                    .body("$", hasKey("avaliacao")).body("avaliacao", nullValue());
        }

        @Test
        @DisplayName("Deve retornar bad request para atualizar reserva com dados invalidos")
        public void deveRetornarBadRequestParaAtualizarReservaComDadosInvalidos() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModelCreated.getId(), "nome", "culinaria", 1, restauranteModelCreated.getHorarioAbertura(), restauranteModelCreated.getHorarioEncerramento(), 1, 1L, enderecoDTO);
            ClienteDTO clienteDTO = new ClienteDTO(clienteModelCreated.getEmail(), "nome", 1, 1L);
            ReservaDTO reservaDTO = new ReservaDTO(null, "", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(reservaDTO)
                    .when().patch("/reserva/{id}", reservaModelCreated.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve atualizar reserva")
        public void deveAtualizarReserva() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModelCreated.getId(), "nome", "culinaria", 1, restauranteModelCreated.getHorarioAbertura(), restauranteModelCreated.getHorarioEncerramento(), 1, 1L, enderecoDTO);
            ClienteDTO clienteDTO = new ClienteDTO(clienteModelCreated.getEmail(), "nome", 1, 1L);
            ReservaDTO reservaDTO = new ReservaDTO(null, "CANCELADA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(reservaDTO)
                    .when().patch("/reserva/{id}", reservaModelCreated.getId())
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body("$", hasKey("id")).body("id", notNullValue())
                    .body("$", hasKey("status")).body("status", equalTo(reservaDTO.getStatus()))
                    .body("$", hasKey("agendamento")).body("agendamento", notNullValue())
                    .body("$", hasKey("quantidadePessoas")).body("quantidadePessoas", equalTo(reservaDTO.getQuantidadePessoas()))
                    .body("$", hasKey("restaurante")).body("restaurante", notNullValue())
                    .body("$", hasKey("cliente")).body("cliente", notNullValue())
                    .body("$", hasKey("avaliacao")).body("avaliacao", nullValue());
        }

    }

    @Nested
    public class ValidaAvaliacao {

        private ReservaModel reservaModelCreated;

        @BeforeEach
        public void setup() {
            ReservaModel reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-08-01 21:00", formatter), 1, restauranteModelCreated, clienteModelCreated, mesaModelCreated, null);
            reservaModelCreated = reservaRepository.save(reservaModel);
        }

        @Test
        @DisplayName("Deve retornar bad request para reserva invalida")
        public void deveRetornarBadRequestParaReservaInvalida() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "", 1, "comentario");

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(avaliacaoDTO)
                    .when().post("/reserva/{reservaId}/avalia", reservaModelCreated.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve criar avaliacao")
        public void deveCriarReserva() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(avaliacaoDTO)
                    .when().post("/reserva/{reservaId}/avalia", reservaModelCreated.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

}
