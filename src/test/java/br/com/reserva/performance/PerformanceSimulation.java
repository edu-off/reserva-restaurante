package br.com.reserva.performance;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.dto.EnderecoDTO;
import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080/reserva-restaurante")
            .header("Content-Type", "application/json");

    private final RestauranteDTO restauranteDTO = new RestauranteDTO(null, "nome", "culinaria",
            1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L,
            new EnderecoDTO(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L));

    private final MesaDTO mesaDTO = new MesaDTO(null, 2);

    ActionBuilder cadastrarCliente = http("cadastrar cliente")
            .post("/cliente")
            .body(StringBody(asJsonString(new ClienteDTO(generateRandomEmail(), "nome", 1, 1L))))
            .check(status().is(201))
            .check(jsonPath("$.email").saveAs("clienteEmail"));

    ActionBuilder cadastrarRestaurante = http("cadastrar restaurante")
            .post("/restaurante")
            .body(StringBody(asJsonString(restauranteDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("restauranteId"));

    ActionBuilder cadastrarMesa = http("cadastrar mesa")
            .post("/restaurante/#{restauranteId}/mesa")
            .body(StringBody(asJsonString(mesaDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("mesaId"));

    ScenarioBuilder cenarioCadastrarCliente = scenario("Cadastrar cliente")
            .exec(cadastrarCliente);

    ScenarioBuilder cenarioCadastrarRestauranteMesa = scenario("Cadastrar restaurante e mesa")
            .exec(cadastrarRestaurante)
            .exec(cadastrarMesa);

    {
        setUp(
                cenarioCadastrarCliente.injectOpen(rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioCadastrarRestauranteMesa.injectOpen(rampUsersPerSec(1).to(30).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30).to(1).during(Duration.ofSeconds(10))))
                .protocols(httpProtocol).assertions(global().responseTime().max().lt(50),
                        global().failedRequests().count().is(0L));
    }



    /*
    ActionBuilder buscarMensagemRequest = http("buscar mensagem")
            .get("/mensagens/#{mensagemId}")
            .check(status().is(200));

    ActionBuilder listarMensagemRequest = http("listar mensagens")
            .get("/mensagens")
            .queryParam("page", "0")
            .queryParam("size", "10")
            .check(status().is(200));

    ActionBuilder removerMensagemRequest = http("remover mensagem")
            .delete("/mensagens/#{mensagemId}")
            .check(status().is(200));

    ScenarioBuilder cenarioListarMensagem = scenario("Listar mensagens")
            .exec(listarMensagemRequest);

    ScenarioBuilder cenarioAdicionarBuscarMensagem = scenario("Adicionar e Buscar mensagem")
            .exec(adicinarMensagemRequest)
            .exec(buscarMensagemRequest);

    ScenarioBuilder cenarioAdicionarRemoverMensagem = scenario("Adicionar e Remover mensagem")
            .exec(adicinarMensagemRequest)
            .exec(removerMensagemRequest);

    {
        setUp(
                cenarioAdicionarMensagem.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarRemoverMensagem.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarBuscarMensagem.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioListarMensagem.injectOpen(
                        rampUsersPerSec(1)
                                .to(100)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(100)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(100)
                                .to(1)
                                .during(Duration.ofSeconds(10))))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(50),
                        global().failedRequests().count().is(0L));
    }
    */

    private static String asJsonString(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateRandomEmail() {
        return UUID.randomUUID().toString() + "@teste.com.br";
    }

}
