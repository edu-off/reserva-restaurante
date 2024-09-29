package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BuscaReservasPorClientePeriodo {

    private final ReservaGateway reservaGateway;

    @Autowired
    public BuscaReservasPorClientePeriodo(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    @Transactional
    public Page<Reserva> execute(String clienteId, LocalDateTime periodoInicial, LocalDateTime periodoFinal, Pageable pageable) {
        return reservaGateway.buscaReservasPorClientePeriodo(clienteId, periodoInicial, periodoFinal, pageable);
    }

}
