package br.com.reserva.domain.entities;

import br.com.reserva.domain.enums.StatusMesa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MesaTest {

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Mesa corretamente")
        public void deveInstanciarMesaCorretamente() {
            Mesa mesa = new Mesa(2, StatusMesa.DISPONIVEL);
            assertThat(mesa).isInstanceOf(Mesa.class);
            assertThat(mesa.getId()).isNull();
            assertThat(mesa.getCapacidade()).isEqualTo(2);
            assertThat(mesa.getStatus()).isInstanceOf(StatusMesa.class).isEqualTo(StatusMesa.DISPONIVEL);
        }

    }

    @Nested
    public class ValidacaoCapacidade {

        @Test
        @DisplayName("Deve lançar exceção para capacidade nula")
        public void deveLancarExcecaoParaCapacidadeNula() {
            Integer capacidadeNula = null;
            assertThatThrownBy(() -> new Mesa(capacidadeNula, StatusMesa.DISPONIVEL))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("capacidade inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para capacidade inferior ao minimo permitido")
        public void deveLancarExcecaoParaCapacidadeInferiorAoMinimoPermitido() {
            Integer capacidadeInferiorAoMinimoPermitido = 0;
            assertThatThrownBy(() -> new Mesa(capacidadeInferiorAoMinimoPermitido, StatusMesa.DISPONIVEL))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("capacidade inferior ao permitido");
        }

        @Test
        @DisplayName("Deve lançar exceção para capacidade superior ao máximo permitido")
        public void deveLancarExcecaoParaCapacidadeSuperiorMaximoPermitido() {
            Integer capacidadeSuperiorAoMaximoPermitido = 11;
            assertThatThrownBy(() -> new Mesa(capacidadeSuperiorAoMaximoPermitido, StatusMesa.DISPONIVEL))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("capacidade superior ao permitido");
        }

    }

    @Nested
    public class ValidacaoStatus {

        @Test
        @DisplayName("Deve lançar exceção para status nulo")
        public void deveLancarExcecaoParaStatusNulo() {
            StatusMesa status = null;
            assertThatThrownBy(() -> new Mesa(1, status))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

    }

}
