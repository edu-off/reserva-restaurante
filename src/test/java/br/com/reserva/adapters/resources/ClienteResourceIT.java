package br.com.reserva.adapters.resources;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.infrastructure.database.models.ClienteModel;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteModel clienteModelCreated;

    @BeforeEach
    public void setup() {
        RestAssured.port  = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", 1, 1L, null);
        clienteModelCreated = clienteRepository.save(clienteModel);
    }

    @Nested
    public class ValidaResource {

        @Test
        @DisplayName("Deve retornar bad request para criacao de cliente invalido")
        public void deveRetornarBadRequestParaCriacaoDeClienteInvalido() {
            ClienteDTO clienteDTO = new ClienteDTO();
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().post("/cliente")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve retornar bad request para cliente ja existente")
        public void deveRetornarBadRequestParaClienteJaExistente() {
            ClienteDTO clienteDTO = new ClienteDTO(clienteModelCreated.getEmail(), "nome", 1, 1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().post("/cliente")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("cliente já existente"));
        }

        @Test
        @DisplayName("Deve criar cliente")
        public void deveCriarCliente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste1@teste1.com.br", "nome", 1, 1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().post("/cliente")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body("$", hasKey("email")).body("email", equalTo(clienteDTO.getEmail()))
                    .body("$", hasKey("nome")).body("nome", equalTo(clienteDTO.getNome()))
                    .body("$", hasKey("ddd")).body("ddd", equalTo(clienteDTO.getDdd()))
                    .body("$", hasKey("telefone")).body("telefone", equalTo(1));
        }

    }

}
