package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.CadastraClientePresenter;
import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.usecases.cliente.SalvaCliente;
import br.com.reserva.application.usecases.cliente.ValidaCliente;
import br.com.reserva.application.usecases.cliente.VerificaExistenciaCliente;
import br.com.reserva.domain.entities.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CadastraClienteController {

    private final VerificaExistenciaCliente verificaExistenciaCliente;
    private final ValidaCliente validaCliente;
    private final SalvaCliente salvaCliente;
    private final CadastraClientePresenter presenter;

    @Autowired
    public CadastraClienteController(VerificaExistenciaCliente verificaExistenciaCliente,
                                     ValidaCliente validaCliente,
                                     SalvaCliente salvaCliente,
                                     CadastraClientePresenter presenter) {
        this.verificaExistenciaCliente = verificaExistenciaCliente;
        this.validaCliente = validaCliente;
        this.salvaCliente = salvaCliente;
        this.presenter = presenter;
    }

    @Transactional
    public ClienteDTO execute(ClienteDTO clienteDTO) {
        Cliente cliente;
        try {
            verificaExistenciaCliente.execute(clienteDTO.getEmail());
            throw new ClienteException("cliente já existente");
        } catch (EntityNotFoundException e) {
            cliente = validaCliente.execute(clienteDTO);
        }
        Cliente clienteCreated = salvaCliente.execute(cliente);
        return presenter.execute(clienteCreated);
    }

}
