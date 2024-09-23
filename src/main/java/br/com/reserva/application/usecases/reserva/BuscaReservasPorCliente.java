package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaReservasPorCliente {

    private final ReservaGateway reservaGateway;

    @Autowired
    public BuscaReservasPorCliente(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Page<Reserva> execute(String clienteId, Pageable pageable) {
        return reservaGateway.buscaReservasPorCliente(clienteId, pageable);
    }

}
