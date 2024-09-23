package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.infrastructure.database.models.MesaModel;
import br.com.reserva.infrastructure.database.repositories.MesaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MesaGatewayImpl implements MesaGateway {

    private final MesaRepository mesaRepository;
    private final ModelMapper mapper;

    @Autowired
    public MesaGatewayImpl(MesaRepository mesaRepository, ModelMapper mapper) {
        this.mesaRepository = mesaRepository;
        this.mapper = mapper;
    }

    @Override
    public Mesa buscaMesaPorId(Long id) {
        Optional<MesaModel> optionalMesaModel = mesaRepository.findById(id);
        return optionalMesaModel.map(mesaModel -> mapper.map(mesaModel, Mesa.class)).orElse(null);
    }

    @Override
    public List<Mesa> buscaMesasPorRestaurante(Long restauranteId) {
        List<Mesa> mesas = new ArrayList<>();
        List<MesaModel> mesasModel = mesaRepository.findAllByRestauranteId(restauranteId);
        mesasModel.parallelStream().forEach(mesaModel -> mesas.add(mapper.map(mesaModel, Mesa.class)));
        return mesas;
    }

    @Override
    public Mesa salvaMesa(Mesa mesa) {
        MesaModel mesaModel = mapper.map(mesa, MesaModel.class);
        mesaModel.setId(null);
        MesaModel mesaModelCreated = mesaRepository.save(mesaModel);
        return mapper.map(mesaModelCreated, Mesa.class);
    }

    @Override
    public Mesa atualizaMesa(Long id, Mesa mesa) {
        Mesa mesaRecuperada = buscaMesaPorId(id);
        if (Objects.isNull(mesaRecuperada))
            return null;
        mesaRecuperada.setCapacidade(mesa.getCapacidade());
        mesaRecuperada.setStatus(mesa.getStatus());
        MesaModel mesaModelUpdated = mesaRepository.save(mapper.map(mesaRecuperada, MesaModel.class));
        return mapper.map(mesaModelUpdated, Mesa.class);
    }

}
