package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.infrastructure.database.models.ClienteModel;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class CadastraClienteControllerIT {

    @Autowired
    private CadastraClienteController cadastraClienteController;

    @Autowired
    private ClienteRepository clienteRepository;

    @Nested
    public class ValidacaoCadastroCliente {

        @Test
        @DisplayName("Deve lancar excecao para cliente ja existente")
        public void deveLancarExcecaoParaClienteJaExistente() {
            String email = "teste@teste.com.br";
            ClienteModel clienteModel = new ClienteModel(email, "nome", 1, 1L, null);
            clienteRepository.save(clienteModel);
            ClienteDTO clienteDTO = new ClienteDTO(email, "nome", 1, 1L);
            assertThatThrownBy(() -> cadastraClienteController.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("cliente já existente");
        }

        @Test
        @DisplayName("Deve lancar excecao na validacao de cliente")
        public void deveLancarExcecaoNaValidacaoDeCliente() {
            ClienteDTO clienteDTO = new ClienteDTO("", "nome", 1, 1L);
            assertThatThrownBy(() -> cadastraClienteController.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("erro ao validar dados do cliente: email inválido");
        }

        @Test
        @DisplayName("Deve cadastrar cliente corretamente")
        public void deveCadastrarClienteCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            ClienteDTO clienteDTOCreated = cadastraClienteController.execute(clienteDTO);
            assertThat(clienteDTOCreated).isInstanceOf(ClienteDTO.class).isNotNull();
            assertThat(clienteDTOCreated.getEmail()).isEqualTo(clienteDTO.getEmail());
            assertThat(clienteDTOCreated.getNome()).isEqualTo(clienteDTO.getNome());
        }

    }

}
