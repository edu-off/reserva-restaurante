package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.CadastraMesaPresenter;
import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.exceptions.MesaException;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.application.usecases.restaurante.SalvaMesa;
import br.com.reserva.application.usecases.restaurante.ValidaMesa;
import br.com.reserva.application.usecases.restaurante.VerificaExistenciaRestaurante;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class CadastraMesaControllerTest {

    @Mock
    private ValidaMesa validaMesa;

    @Mock
    private VerificaExistenciaRestaurante verificaExistenciaRestaurante;

    @Mock
    private SalvaMesa salvaMesa;

    @Mock
    private CadastraMesaPresenter cadastraMesaPresenter;

    @InjectMocks
    private CadastraMesaController cadastraMesaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class CriacaoMesa {

        @Test
        @DisplayName("Deve lancar excecao na validacao de mesa")
        public void deveLancarExcecaoNaValidacaoDeMesa() {
            MesaDTO mesaDTO = new MesaDTO(null, 0);
            when(validaMesa.execute(mesaDTO)).thenThrow(MesaException.class);
            assertThatThrownBy(() -> cadastraMesaController.execute(1L, mesaDTO))
                    .isInstanceOf(MesaException.class);
        }

        @Test
        @DisplayName("Deve lancar excecao na verificacao de existencia de restaurante")
        public void deveLancarExcecaoNaVerificacaoDeExistenciaDeRestaurante() {
            MesaDTO mesaDTO = new MesaDTO(null, 1);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            when(validaMesa.execute(mesaDTO)).thenReturn(mesa);
            when(verificaExistenciaRestaurante.execute(1L)).thenThrow(RestauranteException.class);
            assertThatThrownBy(() -> cadastraMesaController.execute(1L, mesaDTO))
                    .isInstanceOf(RestauranteException.class);
        }

        @Test
        @DisplayName("Deve criar mesa corretamente")
        public void deveCriarMesaCorretamente() {
            MesaDTO mesaDTO = new MesaDTO(null, 1);
            mesaDTO.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Endereco endereco = new Endereco("logradouro", "numero", "bairro", "cidade", "SP", 1L);
            Restaurante restaurante = new Restaurante("nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, endereco);
            when(validaMesa.execute(mesaDTO)).thenReturn(mesa);
            when(verificaExistenciaRestaurante.execute(1L)).thenReturn(restaurante);
            when(salvaMesa.execute(mesa)).thenReturn(mesa);
            when(cadastraMesaPresenter.execute(mesa)).thenReturn(mesaDTO);
            MesaDTO mesaDTOCreated = cadastraMesaController.execute(1L, mesaDTO);
            assertThat(mesaDTOCreated).isInstanceOf(MesaDTO.class).isNotNull();
            assertThat(mesaDTOCreated.getId()).isNotNull().isNotZero();
        }

    }

}
