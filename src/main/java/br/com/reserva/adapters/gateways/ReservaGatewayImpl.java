package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.infrastructure.database.models.MesaModel;
import br.com.reserva.infrastructure.database.models.ReservaModel;
import br.com.reserva.infrastructure.database.repositories.ReservaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ReservaGatewayImpl implements ReservaGateway {

    private final ReservaRepository reservaRepository;
    private final ModelMapper mapper;

    @Autowired
    public ReservaGatewayImpl(ReservaRepository reservaRepository, ModelMapper mapper) {
        this.reservaRepository = reservaRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<Reserva> buscaReservasPorRestaurante(Long restauranteId, Pageable pageable) {
        Page<ReservaModel> queryResults = reservaRepository.findByRestauranteId(restauranteId, pageable);
        return transformPageOfModelToPageOfDomain(queryResults, pageable);
    }

    @Override
    public Page<Reserva> buscaReservasPorRestaurantePeriodo(Long restauranteId, LocalDateTime periodoInicial, LocalDateTime periodoFinal, Pageable pageable) {
        Page<ReservaModel> queryResults = reservaRepository.findByRestauranteIdPeriodo(restauranteId, periodoInicial, periodoFinal, pageable);
        return transformPageOfModelToPageOfDomain(queryResults, pageable);
    }

    @Override
    public Page<Reserva> buscaReservasPorCliente(String clienteId, Pageable pageable) {
        Page<ReservaModel> queryResults = reservaRepository.findByClienteId(clienteId, pageable);
        return transformPageOfModelToPageOfDomain(queryResults, pageable);
    }

    @Override
    public Page<Reserva> buscaReservasPorClientePeriodo(String clienteId, LocalDateTime periodoInicial, LocalDateTime periodoFinal, Pageable pageable) {
        Page<ReservaModel> queryResults = reservaRepository.findByClienteIdPeriodo(clienteId, periodoInicial, periodoFinal, pageable);
        return transformPageOfModelToPageOfDomain(queryResults, pageable);
    }

    @Override
    public Reserva buscaReservaPorId(Long id) {
        Optional<ReservaModel> optionalReservaModel = reservaRepository.findById(id);
        return optionalReservaModel.map(reservaModel -> mapper.map(reservaModel, Reserva.class)).orElse(null);
    }

    @Override
    public Reserva salvaReserva(Reserva reserva) {
        ReservaModel reservaModel = mapper.map(reserva, ReservaModel.class);
        reservaModel.setId(null);
        ReservaModel reservaModelCreated = reservaRepository.save(reservaModel);
        return mapper.map(reservaModelCreated, Reserva.class);
    }

    @Override
    public Reserva atualizaReserva(Long id, Reserva reserva) {
        Reserva reservaRecuperada = buscaReservaPorId(id);
        if (Objects.isNull(reservaRecuperada))
            return null;
        reservaRecuperada.setStatus(reserva.getStatus());
        reservaRecuperada.setAgendamento(reserva.getAgendamento());
        reservaRecuperada.setQuantidadePessoas(reserva.getQuantidadePessoas());
        ReservaModel reservaModelUpdated = reservaRepository.save(mapper.map(reservaRecuperada, ReservaModel.class));
        return mapper.map(reservaModelUpdated, Reserva.class);
    }

    private Page<Reserva> transformPageOfModelToPageOfDomain(Page<ReservaModel> queryResults, Pageable pageable) {
        List<ReservaModel> models = queryResults.getContent();
        List<Reserva> reservas = new ArrayList<>();
        models.forEach(model -> reservas.add(mapper.map(model, Reserva.class)));
        return PageableExecutionUtils.getPage(reservas, pageable, reservas::size);
    }

}
