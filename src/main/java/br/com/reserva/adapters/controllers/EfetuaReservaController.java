package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.ReservaPresenter;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.usecases.cliente.VerificaExistenciaCliente;
import br.com.reserva.application.usecases.reserva.AtualizaMesa;
import br.com.reserva.application.usecases.reserva.ConsultaMesaDisponivel;
import br.com.reserva.application.usecases.reserva.RegistraReserva;
import br.com.reserva.application.usecases.reserva.ValidaReserva;
import br.com.reserva.application.usecases.restaurante.VerificaExistenciaRestaurante;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class EfetuaReservaController {

    private final ValidaReserva validaReserva;
    private final VerificaExistenciaCliente verificaExistenciaCliente;
    private final VerificaExistenciaRestaurante verificaExistenciaRestaurante;
    private final ConsultaMesaDisponivel consultaMesaDisponivel;
    private final RegistraReserva registraReserva;
    private final AtualizaMesa atualizaMesa;
    private final ReservaPresenter presenter;

    @Autowired
    public EfetuaReservaController(ValidaReserva validaReserva,
                         VerificaExistenciaCliente verificaExistenciaCliente,
                         VerificaExistenciaRestaurante verificaExistenciaRestaurante,
                         ConsultaMesaDisponivel consultaMesaDisponivel,
                         RegistraReserva registraReserva,
                         AtualizaMesa atualizaMesa,
                         ReservaPresenter presenter) {
        this.validaReserva = validaReserva;
        this.verificaExistenciaCliente = verificaExistenciaCliente;
        this.verificaExistenciaRestaurante = verificaExistenciaRestaurante;
        this.consultaMesaDisponivel = consultaMesaDisponivel;
        this.registraReserva = registraReserva;
        this.atualizaMesa = atualizaMesa;
        this.presenter = presenter;
    }

    @Transactional
    public ReservaDTO execute(ReservaDTO reservaDTO) {
        Cliente cliente = verificaExistenciaCliente.execute(reservaDTO.getCliente().getEmail());
        Restaurante restaurante = verificaExistenciaRestaurante.execute(reservaDTO.getRestaurante().getId());
        Mesa mesa = consultaMesaDisponivel.execute(reservaDTO.getQuantidadePessoas(), reservaDTO.getRestaurante().getId());
        if (Objects.isNull(mesa))
            throw new ReservaException("restaurante não possui mesa com a quantidade de pessoas solicitada");
        Reserva reserva = validaReserva.execute(reservaDTO, restaurante, mesa, cliente);
        Reserva reservaCreated = registraReserva.execute(reserva);
        if (Objects.isNull(reservaCreated))
            throw new ReservaException("erro ao salvar reserva");
        atualizaMesa.execute(mesa.getId(), StatusMesa.RESERVADA);
        return presenter.execute(reservaCreated);
    }

}
