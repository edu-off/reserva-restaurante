package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.ReservaPresenter;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.usecases.reserva.*;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GerenciaReservaController {

    private final BuscaReservasPorId buscaReservasPorId;
    private final AlteraDadosReserva alteraDadosReserva;
    private final ValidaReserva validaReserva;
    private final AtualizaReserva atualizaReserva;
    private final AtualizaMesa atualizaMesa;
    private final ReservaPresenter presenter;
    private final ModelMapper mapper;

    @Autowired
    public GerenciaReservaController(BuscaReservasPorId buscaReservasPorId,
                                     AlteraDadosReserva alteraDadosReserva,
                                     ValidaReserva validaReserva,
                                     AtualizaReserva atualizaReserva,
                                     AtualizaMesa atualizaMesa,
                                     ReservaPresenter presenter,
                                     ModelMapper mapper) {
        this.buscaReservasPorId = buscaReservasPorId;
        this.alteraDadosReserva = alteraDadosReserva;
        this.validaReserva = validaReserva;
        this.atualizaReserva = atualizaReserva;
        this.atualizaMesa = atualizaMesa;
        this.presenter = presenter;
        this.mapper = mapper;
    }

    @Transactional
    public ReservaDTO execute(Long id, ReservaDTO reservaDTO) {
        Reserva reserva = buscaReservasPorId.execute(id);
        Reserva reservaChanged = alteraDadosReserva.execute(reserva, reservaDTO);
        Reserva reservaValidated = validaReserva.execute(mapper.map(reservaChanged, ReservaDTO.class),
                reservaChanged.getRestaurante(), reserva.getMesa(), reservaChanged.getCliente());
        Reserva reservaUpdated = atualizaReserva.execute(id, reservaValidated);
        StatusMesa statusMesa = reservaUpdated.getStatus().equals(StatusReserva.ATIVA) ? StatusMesa.RESERVADA : StatusMesa.DISPONIVEL;
        atualizaMesa.execute(reservaUpdated.getMesa().getId(), statusMesa);
        return presenter.execute(reservaUpdated);
    }

}
