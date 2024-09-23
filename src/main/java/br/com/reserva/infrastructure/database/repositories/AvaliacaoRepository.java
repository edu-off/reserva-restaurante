package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.AvaliacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {
}
