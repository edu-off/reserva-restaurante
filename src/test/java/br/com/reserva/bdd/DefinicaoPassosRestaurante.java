package br.com.reserva.bdd;

import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassosRestaurante {

    private Response response;

    private RestauranteDTO restauranteResponse;

    private final String endpoint = "http://localhost:8080/reserva-restaurante/restaurante";

    @Quando("submeter um novo restaurante")
    public RestauranteDTO submeterUmNovoRestaurante() {
        RestauranteDTO restauranteDTO = new RestauranteDTO(null, "nome", "culinaria",
                1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L,
                new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L));
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteDTO).when().post(endpoint);
        return response.then().extract().as(RestauranteDTO.class);
    }

    @Entao("o restaurante é registrado com sucesso")
    public void restauranteRegistradoComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestauranteResponseSchema.json"));
    }

    @Dado("que um restaurante já foi registrado")
    public void restauranteJaPublicado() {
        restauranteResponse = submeterUmNovoRestaurante();
    }

    @Quando("requisitar o registro de uma mesa")
    public MesaDTO requisitarRegistroDeMesa() {
        MesaDTO mesaDTO = new MesaDTO(null, 2);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mesaDTO).when().post(endpoint + "/{restauranteId}/mesa", restauranteResponse.getId());
        MesaDTO newMesaDTO = response.then().extract().as(MesaDTO.class);
        return newMesaDTO;
    }

    @Entao("a mesa é registrada com sucesso")
    public void mesaRegistradaComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/MesaResponseSchema.json"));
    }

}
