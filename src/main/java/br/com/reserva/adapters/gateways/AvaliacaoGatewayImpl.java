package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.AvaliacaoGateway;
import br.com.reserva.domain.entities.Avaliacao;
import br.com.reserva.infrastructure.database.models.AvaliacaoModel;
import br.com.reserva.infrastructure.database.repositories.AvaliacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoGatewayImpl implements AvaliacaoGateway {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ModelMapper mapper;

    @Autowired
    public AvaliacaoGatewayImpl(AvaliacaoRepository avaliacaoRepository, ModelMapper mapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.mapper = mapper;
    }

    @Override
    public Avaliacao salvaAvaliacao(Avaliacao avaliacao) {
        AvaliacaoModel avaliaModel = mapper.map(avaliacao, AvaliacaoModel.class);
        avaliaModel.setId(null);
        AvaliacaoModel avaliaModelCreated = avaliacaoRepository.save(avaliaModel);
        return mapper.map(avaliaModelCreated, Avaliacao.class);
    }

}
