package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.EnderecoGateway;
import br.com.reserva.domain.objectsvalue.Endereco;
import jakarta.transaction.Transactional;
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
public class EnderecoGatewayIT {

    @Autowired
    private EnderecoGateway enderecoGateway;

    @Nested
    public class ValidacaoEnderecoGateway {

        @Test
        @DisplayName("Deve salvar dominio endereco")
        public void deveSalvarDominioEndereco() {
            Endereco endereco = new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Endereco enderecoCreated = enderecoGateway.salvaEndereco(endereco);
            assertThat(enderecoCreated).isInstanceOf(Endereco.class).isNotNull();
            assertThat(enderecoCreated.getId()).isNotNull().isNotZero();
            assertThat(enderecoCreated.getLogradouro()).isEqualTo(endereco.getLogradouro());
            assertThat(enderecoCreated.getNumero()).isEqualTo(endereco.getNumero());
            assertThat(enderecoCreated.getBairro()).isEqualTo(endereco.getBairro());
            assertThat(enderecoCreated.getCidade()).isEqualTo(endereco.getCidade());
            assertThat(enderecoCreated.getUf()).isEqualTo(endereco.getUf());
            assertThat(enderecoCreated.getCep()).isEqualTo(endereco.getCep());
            assertThat(enderecoCreated.getRestaurante()).isNull();
        }

    }

}
