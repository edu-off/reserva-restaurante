package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.EnderecoModel;
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
public class EnderecoRepositoryIT {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Nested
    public class ValidacaoEnderecoRepository {

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection endereco")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionEndereco() {
            EnderecoModel enderecoModel = new EnderecoModel(null, "logradouro", "numero", "bairro", "cidade", "SP", 1L, null);
            EnderecoModel enderecoModelCreated = enderecoRepository.save(enderecoModel);
            Optional<EnderecoModel> enderecoRetrieved = enderecoRepository.findById(enderecoModelCreated.getId());
            Assertions.assertThat(enderecoRetrieved.isPresent()).isTrue();
            Assertions.assertThat(enderecoRetrieved.get()).isInstanceOf(EnderecoModel.class).isNotNull();
            Assertions.assertThat(enderecoRetrieved.get().getId()).isNotNull();
            Assertions.assertThat(enderecoRetrieved.get().getLogradouro()).isEqualTo(enderecoModel.getLogradouro());
            Assertions.assertThat(enderecoRetrieved.get().getNumero()).isEqualTo(enderecoModel.getNumero());
            Assertions.assertThat(enderecoRetrieved.get().getBairro()).isEqualTo(enderecoModel.getBairro());
            Assertions.assertThat(enderecoRetrieved.get().getCidade()).isEqualTo(enderecoModel.getCidade());
            Assertions.assertThat(enderecoRetrieved.get().getUf()).isEqualTo(enderecoModel.getUf());
            Assertions.assertThat(enderecoRetrieved.get().getCep()).isEqualTo(enderecoModel.getCep());
            Assertions.assertThat(enderecoRetrieved.get().getRestaurante()).isNull();
        }

    }

}
