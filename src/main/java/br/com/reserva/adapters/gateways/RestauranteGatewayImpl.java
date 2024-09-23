package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class RestauranteGatewayImpl implements RestauranteGateway {

    private final RestauranteRepository restauranteRepository;
    private final ModelMapper mapper;

    @Autowired
    public RestauranteGatewayImpl(RestauranteRepository restauranteRepository, ModelMapper mapper) {
        this.restauranteRepository = restauranteRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<Restaurante> buscaRestaurantesPorNome(String nome, Pageable pageable) {
        Page<RestauranteModel> queryResults = restauranteRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return transformPageOfModelToPageOfDomain(queryResults, pageable);
    }

    @Override
    public Page<Restaurante> buscaRestaurantesPorCulinaria(String culinaria, Pageable pageable) {
        Page<RestauranteModel> queryResults = restauranteRepository.findByCulinariaContainingIgnoreCase(culinaria, pageable);
        return transformPageOfModelToPageOfDomain(queryResults, pageable);
    }

    @Override
    public Restaurante buscaRestaurantePorId(Long id) {
        Optional<RestauranteModel> optionalRestauranteModel = restauranteRepository.findById(id);
        return optionalRestauranteModel.map(restauranteModel -> mapper.map(restauranteModel, Restaurante.class)).orElse(null);
    }

    @Override
    public Restaurante salvaRestaurante(Restaurante restaurante) {
        RestauranteModel restauranteModel = mapper.map(restaurante, RestauranteModel.class);
        restauranteModel.setId(null);
        RestauranteModel restauranteModelCreated = restauranteRepository.save(restauranteModel);
        return mapper.map(restauranteModelCreated, Restaurante.class);
    }

    @Override
    public Restaurante atualizaRestaurante(Long id, Restaurante restaurante) {
        Restaurante restauranteRecuperado = buscaRestaurantePorId(id);
        if (Objects.isNull(restauranteRecuperado))
            return null;
        restauranteRecuperado.setNome(restaurante.getNome());
        restauranteRecuperado.setCulinaria(restaurante.getCulinaria());
        restauranteRecuperado.setCapacidade(restaurante.getCapacidade());
        restauranteRecuperado.setHorarioAbertura(restaurante.getHorarioAbertura());
        restauranteRecuperado.setHorarioEncerramento(restaurante.getHorarioEncerramento());
        restauranteRecuperado.setDdd(restaurante.getDdd());
        restauranteRecuperado.setTelefone(restaurante.getTelefone());
        restauranteRecuperado.setMesas(restaurante.getMesas());
        return salvaRestaurante(restauranteRecuperado);
    }

    private Page<Restaurante> transformPageOfModelToPageOfDomain(Page<RestauranteModel> queryResults, Pageable pageable) {
        List<RestauranteModel> models = queryResults.getContent();
        List<Restaurante> restaurantes = new ArrayList<>();
        models.forEach(model -> restaurantes.add(mapper.map(model, Restaurante.class)));
        return PageableExecutionUtils.getPage(restaurantes, pageable, restaurantes::size);
    }

}
