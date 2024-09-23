package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.ClienteModel;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ClienteRepositoryIT {

    @Autowired
    private ClienteRepository clienteRepository;

    @Nested
    public class ValidacaoClienteRepository {

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection cliente")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionCliente() {
            ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", 1, 1L, null);
            ClienteModel clienteCreated = clienteRepository.save(clienteModel);
            Optional<ClienteModel> clienteRetrieved = clienteRepository.findById(clienteCreated.getEmail());
            Assertions.assertThat(clienteRetrieved.isPresent()).isTrue();
            Assertions.assertThat(clienteRetrieved.get()).isInstanceOf(ClienteModel.class).isNotNull();
            Assertions.assertThat(clienteRetrieved.get().getEmail()).isEqualTo(clienteModel.getEmail());
            Assertions.assertThat(clienteRetrieved.get().getNome()).isEqualTo(clienteModel.getNome());
            Assertions.assertThat(clienteRetrieved.get().getDdd()).isEqualTo(clienteModel.getDdd());
            Assertions.assertThat(clienteRetrieved.get().getTelefone()).isEqualTo(clienteModel.getTelefone());
            Assertions.assertThat(clienteRetrieved.get().getReservas()).isNull();
        }

    }

}
