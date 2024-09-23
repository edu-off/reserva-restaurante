package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, String> {
}
