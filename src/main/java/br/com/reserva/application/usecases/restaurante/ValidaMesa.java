package br.com.reserva.application.usecases.restaurante;

import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.exceptions.MesaException;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.enums.StatusMesa;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static br.com.reserva.application.factories.MesaFactory.createMesa;

@Service
public class ValidaMesa {

    @Transactional
    public Mesa execute(MesaDTO mesaDTO) {
        Mesa mesa;
        try {
            mesa = createMesa(mesaDTO, StatusMesa.DISPONIVEL);
        } catch(IllegalArgumentException exception) {
            throw new MesaException("erro ao validar mesa: " + exception.getMessage());
        }
        return mesa;
    }

}
