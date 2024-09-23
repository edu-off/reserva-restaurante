package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.MesaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<MesaModel, Long> {

    List<MesaModel> findAllByRestauranteId(Long restauranteId);

}
