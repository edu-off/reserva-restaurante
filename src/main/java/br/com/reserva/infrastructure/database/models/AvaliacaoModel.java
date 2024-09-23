package br.com.reserva.infrastructure.database.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliacao")
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String comentario;

    private Integer nota;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reserva_id", referencedColumnName = "id")
    private ReservaModel reserva;

}
