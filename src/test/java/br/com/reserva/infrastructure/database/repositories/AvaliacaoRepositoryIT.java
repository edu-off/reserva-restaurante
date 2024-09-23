package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.AvaliacaoModel;
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
public class AvaliacaoRepositoryIT {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Nested
    public class ValidacaoAvaliacaoRepository {

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection avaliacao")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionAvaliacao() {
            AvaliacaoModel avaliacaoModel = new AvaliacaoModel(null, "titulo", "comentario", 1, null);
            AvaliacaoModel avaliacaoCreated = avaliacaoRepository.save(avaliacaoModel);
            Optional<AvaliacaoModel> avaliacaoRetrieved = avaliacaoRepository.findById(avaliacaoCreated.getId());
            Assertions.assertThat(avaliacaoRetrieved.isPresent()).isTrue();
            Assertions.assertThat(avaliacaoRetrieved.get()).isInstanceOf(AvaliacaoModel.class).isNotNull();
            Assertions.assertThat(avaliacaoRetrieved.get().getId()).isNotNull();
            Assertions.assertThat(avaliacaoRetrieved.get().getTitulo()).isEqualTo(avaliacaoModel.getTitulo());
            Assertions.assertThat(avaliacaoRetrieved.get().getNota()).isEqualTo(avaliacaoModel.getNota());
            Assertions.assertThat(avaliacaoRetrieved.get().getComentario()).isEqualTo(avaliacaoModel.getComentario());
        }

    }

}
