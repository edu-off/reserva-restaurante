package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.CadastraMesaPresenter;
import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.usecases.restaurante.SalvaMesa;
import br.com.reserva.application.usecases.restaurante.ValidaMesa;
import br.com.reserva.application.usecases.restaurante.VerificaExistenciaRestaurante;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CadastraMesaController {

    private final ValidaMesa validaMesa;
    private final VerificaExistenciaRestaurante verificaExistenciaRestaurante;
    private final SalvaMesa salvaMesa;
    private final CadastraMesaPresenter presenter;

    @Autowired
    public CadastraMesaController(ValidaMesa validaMesa, VerificaExistenciaRestaurante verificaExistenciaRestaurante,
                                  SalvaMesa salvaMesa, CadastraMesaPresenter presenter) {
        this.validaMesa = validaMesa;
        this.verificaExistenciaRestaurante = verificaExistenciaRestaurante;
        this.salvaMesa = salvaMesa;
        this.presenter = presenter;
    }

    public MesaDTO execute(Long restauranteId, MesaDTO mesaDTO) {
        Mesa mesa = validaMesa.execute(mesaDTO);
        Restaurante restaurante  = verificaExistenciaRestaurante.execute(restauranteId);
        mesa.setRestaurante(restaurante);
        Mesa mesaCreated = salvaMesa.execute(mesa);
        return presenter.execute(mesaCreated);
    }

}
