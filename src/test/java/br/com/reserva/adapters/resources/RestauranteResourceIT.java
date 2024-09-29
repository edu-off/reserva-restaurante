package br.com.reserva.adapters.resources;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
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

import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestauranteResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setup() {
        RestAssured.port  = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        LocalTime abertura = LocalTime.parse("12:00");
        LocalTime encerramento = LocalTime.parse("23:00");
        RestauranteModel restaurante1 = new RestauranteModel(null, "restaurante 1", "culinaria japonesa", 1, abertura, encerramento, 1, 1L, null, null, null);
        RestauranteModel restaurante2 = new RestauranteModel(null, "restaurante 2", "culinaria arabe", 1, abertura, encerramento, 1, 1L, null, null, null);
        RestauranteModel restaurante3 = new RestauranteModel(null, "teste 1", "culinaria japonesa", 1, abertura, encerramento, 1, 1L, null, null, null);
        RestauranteModel restaurante4 = new RestauranteModel(null, "teste 2", "culinaria arabe", 1, abertura, encerramento, 1, 1L, null, null, null);
        restauranteRepository.saveAll(List.of(restaurante1, restaurante2, restaurante3, restaurante4));
    }

    @Nested
    public class ValidaBuscas {

        @Test
        @DisplayName("Deve retornar restaurante por nome")
        public void deveRetornarRestaurantesPorNome() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/restaurante/busca-nome/{nome}", "restaurante")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ListaRestauranteResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar restaurante por culinaria")
        public void deveRetornarRestaurantesPorCulinaria() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/restaurante/busca-culinaria/{culinaria}", "arabe")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ListaRestauranteResponseSchema.json"));
        }

    }

    @Nested
    public class ValidaRestaurante {

        @Test
        @DisplayName("Deve retornar bad request para criacao de restaurante invalido")
        public void deveRetornarBadRequestParaCriacaoDeRestauranteInvalido() {
            LocalTime abertura = LocalTime.parse("12:00");
            LocalTime encerramento = LocalTime.parse("23:00");
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, null, "culinaria", 1, abertura, encerramento, 1, 1L, enderecoDTO);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(restauranteDTO)
                    .when().post("/restaurante")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve criar restaurante")
        public void deveCriarRestaurante() {
            LocalTime abertura = LocalTime.parse("12:00");
            LocalTime encerramento = LocalTime.parse("23:00");
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(null, "nome", "culinaria", 1, abertura, encerramento, 1, 1L, enderecoDTO);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(restauranteDTO)
                    .when().post("/restaurante")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body("$", hasKey("id")).body("id", notNullValue())
                    .body("$", hasKey("nome")).body("nome", equalTo(restauranteDTO.getNome()))
                    .body("$", hasKey("culinaria")).body("culinaria", equalTo(restauranteDTO.getCulinaria()))
                    .body("$", hasKey("capacidade")).body("capacidade", equalTo(restauranteDTO.getCapacidade()))
                    .body("$", hasKey("horarioAbertura")).body("horarioAbertura", notNullValue())
                    .body("$", hasKey("horarioEncerramento")).body("horarioEncerramento", notNullValue())
                    .body("$", hasKey("ddd")).body("ddd", equalTo(restauranteDTO.getDdd()))
                    .body("$", hasKey("telefone")).body("telefone", equalTo(1));
        }

    }

    @Nested
    public class ValidaMesa {

        @Test
        @DisplayName("Deve retornar bad request para criacao de mesa invalida")
        public void deveRetornarBadRequestParaCriacaoDeMesaInvalida() {
            LocalTime abertura = LocalTime.parse("12:00");
            LocalTime encerramento = LocalTime.parse("23:00");
            RestauranteModel restauranteModel = new RestauranteModel(null, "teste 2", "culinaria arabe", 1, abertura, encerramento, 1, 1L, null, null, null);
            RestauranteModel restauranteModelCreated = restauranteRepository.save(restauranteModel);
            MesaDTO mesaDTO = new MesaDTO(null, 0);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(mesaDTO)
                    .when().post("/restaurante/{restauranteId}/mesa", restauranteModelCreated.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve criar mesa")
        public void deveCriarMesa() {
            LocalTime abertura = LocalTime.parse("12:00");
            LocalTime encerramento = LocalTime.parse("23:00");
            RestauranteModel restauranteModel = new RestauranteModel(null, "teste 2", "culinaria arabe", 1, abertura, encerramento, 1, 1L, null, null, null);
            RestauranteModel restauranteModelCreated = restauranteRepository.save(restauranteModel);
            MesaDTO mesaDTO = new MesaDTO(null, 2);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(mesaDTO)
                    .when().post("/restaurante/{restauranteId}/mesa", restauranteModelCreated.getId())
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body("$", hasKey("id")).body("id", notNullValue())
                    .body("$", hasKey("capacidade")).body("capacidade", equalTo(mesaDTO.getCapacidade()));
        }

    }

}
