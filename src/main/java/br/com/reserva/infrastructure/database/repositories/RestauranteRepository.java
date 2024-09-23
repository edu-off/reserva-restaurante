package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.RestauranteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteModel, Long> {

    Page<RestauranteModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<RestauranteModel> findByCulinariaContainingIgnoreCase(String culinaria, Pageable pageable);

}
