package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.infrastructure.database.models.ReservaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    @Query("select reserva from ReservaModel reserva where reserva.restaurante.id = :id")
    Page<ReservaModel> findByRestauranteId(Long id, Pageable pageable);

    @Query("select reserva from ReservaModel reserva where reserva.restaurante.id = :id and reserva.agendamento between :inicio and :fim")
    Page<ReservaModel> findByRestauranteIdPeriodo(Long id, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    @Query("select reserva from ReservaModel reserva where reserva.cliente.email = :id")
    Page<ReservaModel> findByClienteId(String id, Pageable pageable);

    @Query("select reserva from ReservaModel reserva where reserva.cliente.email = :id and reserva.agendamento between :inicio and :fim")
    Page<ReservaModel> findByClienteIdPeriodo(String id, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

}
