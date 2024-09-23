package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.exceptions.MesaException;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.enums.StatusMesa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidaMesasTest {

    private final ValidaMesa validaMesa = new ValidaMesa();
    private final Integer capacidade = 1;
    private final StatusMesa status = StatusMesa.DISPONIVEL;

    @Nested
    public class ValidacaoTransformacaoDtoParaDominio {

        @Test
        @DisplayName("Deve lançar exceção para mesa com capacidade inválida")
        public void deveLancarExcecaoParaMesaComCapacidadeInvalida() {
            Integer capacidadeInvalida = 0;
            MesaDTO mesaDto = new MesaDTO(1L, capacidadeInvalida);
            assertThatThrownBy(() -> validaMesa.execute(mesaDto))
                    .isInstanceOf(MesaException.class)
                    .hasMessage("erro ao validar mesa: capacidade inferior ao permitido");
        }

        @Test
        @DisplayName("Deve retornar mesa corretamente")
        public void deveRetornarMesaCorretamente() {
            MesaDTO mesaDto = new MesaDTO(1L, capacidade);
            Mesa mesa = validaMesa.execute(mesaDto);
            assertThat(mesa).isInstanceOf(Mesa.class);
            assertThat(mesa.getCapacidade()).isEqualTo(mesaDto.getCapacidade());
            assertThat(mesa.getStatus()).isEqualTo(StatusMesa.DISPONIVEL);
        }

    }

}
