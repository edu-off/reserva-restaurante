package br.com.reserva.infrastructure.database.models;

import br.com.reserva.domain.enums.StatusMesa;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesa")
public class MesaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    private StatusMesa status;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id")
    private RestauranteModel restaurante;

    @OneToMany(mappedBy = "mesa")
    private List<ReservaModel> reservas;

}
