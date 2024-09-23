package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.application.usecases.reserva.RegistraAvaliacao;
import br.com.reserva.application.usecases.reserva.ValidaAvaliacao;
import br.com.reserva.application.usecases.reserva.VerificaExistenciaReserva;
import br.com.reserva.domain.entities.Avaliacao;
import br.com.reserva.domain.entities.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AvaliaReservaController {

    private final VerificaExistenciaReserva verificaExistenciaReserva;
    private final ValidaAvaliacao validaAvaliacao;
    private final RegistraAvaliacao registraAvaliacao;

    @Autowired
    public AvaliaReservaController(VerificaExistenciaReserva verificaExistenciaReserva,
                                   ValidaAvaliacao validaAvaliacao,
                                   RegistraAvaliacao registraAvaliacao) {
        this.verificaExistenciaReserva = verificaExistenciaReserva;
        this.validaAvaliacao = validaAvaliacao;
        this.registraAvaliacao = registraAvaliacao;
    }

    @Transactional
    public void execute(Long reservaId, AvaliacaoDTO avaliacaoDTO) {
        Reserva reserva = verificaExistenciaReserva.execute(reservaId);
        Avaliacao avaliacao = validaAvaliacao.execute(avaliacaoDTO);
        avaliacao.setReserva(reserva);
        registraAvaliacao.execute(avaliacao);
    }

}
