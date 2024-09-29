package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.domain.entities.Mesa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaMesa {

    private final MesaGateway mesaGateway;

    @Autowired
    public SalvaMesa(MesaGateway mesaGateway) {
        this.mesaGateway = mesaGateway;
    }

    @Transactional
    public Mesa execute(Mesa mesa) {
        return mesaGateway.salvaMesa(mesa);
    }

}
