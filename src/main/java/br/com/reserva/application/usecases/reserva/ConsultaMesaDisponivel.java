package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.enums.StatusMesa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaMesaDisponivel {

    private final MesaGateway mesaGateway;

    @Autowired
    public ConsultaMesaDisponivel(MesaGateway mesaGateway) {
        this.mesaGateway = mesaGateway;
    }

    @Transactional
    public Mesa execute(Integer quantidadePessoas, Long restauranteId) {
        List<Mesa> mesas = mesaGateway.buscaMesasPorRestaurante(restauranteId);
        Optional<Mesa> mesa = mesas.stream()
                .filter(filter -> filter.getStatus().equals(StatusMesa.DISPONIVEL))
                .filter(filter -> filter.getCapacidade() >= quantidadePessoas)
                .findFirst();

        return mesa.orElse(null);
    }

}
