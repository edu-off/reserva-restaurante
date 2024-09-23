package br.com.reserva.infrastructure.database.models;

import br.com.reserva.domain.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva")
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    private LocalDateTime agendamento;

    @Column(name = "quantidade_pessoas")
    private Integer quantidadePessoas;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id")
    private RestauranteModel restaurante;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cliente_email", referencedColumnName = "email")
    private ClienteModel cliente;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mesa_id", referencedColumnName = "id")
    private MesaModel mesa;

    @OneToOne(mappedBy = "reserva")
    private AvaliacaoModel avaliacao;

}
