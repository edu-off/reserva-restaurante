package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.enums.StatusMesa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AtualizaMesa {

    private final MesaGateway mesaGateway;

    @Autowired
    public AtualizaMesa(MesaGateway mesaGateway) {
        this.mesaGateway = mesaGateway;
    }

    @Transactional
    public void execute(Long mesaId, StatusMesa status) {
        Mesa mesa = mesaGateway.buscaMesaPorId(mesaId);
        if (Objects.isNull(mesa))
            throw new ReservaException("erro ao ocupar mesa: mesa não encontrada");

        mesa.setStatus(status);
        mesaGateway.atualizaMesa(mesaId, mesa);
    }

}
