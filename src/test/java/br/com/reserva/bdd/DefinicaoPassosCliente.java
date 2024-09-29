package br.com.reserva.bdd;

import br.com.reserva.application.dto.ClienteDTO;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassosCliente {

    private Response response;

    private final String endpoint = "http://localhost:8080/reserva-restaurante/cliente";

    @Quando("submeter um novo cliente")
    public ClienteDTO submeterUmNovoCliente() {
        ClienteDTO clienteDTO = new ClienteDTO(generateRandomEmail(), "nome", 1, 1L);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteDTO).when().post(endpoint);
        return response.getBody().as(ClienteDTO.class);
    }

    @Entao("o cliente é registrado com sucesso")
    public void clienteRegistradoComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    private String generateRandomEmail() {
        return UUID.randomUUID() + "@" + UUID.randomUUID() + ".com.br";
    }

}
