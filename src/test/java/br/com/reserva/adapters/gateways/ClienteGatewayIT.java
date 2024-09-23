package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ClienteGatewayIT {

    @Autowired
    private ClienteGateway clienteGateway;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
    }

    @Nested
    public class ValidacaoClienteGateway {

        @Test
        @DisplayName("Deve salvar dominio cliente")
        public void deveSalvarDominioCliente() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Cliente clienteCreated = clienteGateway.salvaCliente(cliente);
            assertThat(clienteCreated).isInstanceOf(Cliente.class).isNotNull();
            assertThat(clienteCreated.getEmail()).isEqualTo(cliente.getEmail());
            assertThat(clienteCreated.getNome()).isEqualTo(cliente.getNome());
            assertThat(clienteCreated.getDdd()).isEqualTo(cliente.getDdd());
            assertThat(clienteCreated.getTelefone()).isEqualTo(cliente.getTelefone());
        }

        @Test
        @DisplayName("Deve buscar cliente corretamente pelo id")
        public void deveBuscarClienteCorretamentePeloId() {
            Cliente clienteCreated1 = clienteGateway.salvaCliente(new Cliente("teste1@teste1.com.br", "nome1", 1, 1L));
            Cliente clienteCreated2 = clienteGateway.salvaCliente(new Cliente("teste2@teste2.com.br", "nome2", 2, 2L));
            Cliente clienteRetrieved = clienteGateway.buscaClientePorId(clienteCreated1.getEmail());
            assertThat(clienteRetrieved).isInstanceOf(Cliente.class).isNotNull();
            assertThat(clienteRetrieved.getEmail()).isNotNull().isEqualTo(clienteCreated1.getEmail());
            assertThat(clienteRetrieved.getNome()).isEqualTo(clienteCreated1.getNome()).isNotEqualTo(clienteCreated2.getNome());
            assertThat(clienteRetrieved.getDdd()).isEqualTo(clienteCreated1.getDdd()).isNotEqualTo(clienteCreated2.getDdd());
            assertThat(clienteRetrieved.getTelefone()).isEqualTo(clienteCreated1.getTelefone()).isNotEqualTo(clienteCreated2.getTelefone());
        }

        @Test
        @DisplayName("Deve buscar cliente por id que nao existe")
        public void deveBuscarClientePorIdQueNaoExiste() {
            Cliente clienteRetrieved = clienteGateway.buscaClientePorId("teste3@teste3.com.br");
            assertThat(clienteRetrieved).isNull();
        }

    }

}
