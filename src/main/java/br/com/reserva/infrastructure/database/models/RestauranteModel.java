package br.com.reserva.infrastructure.database.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurante")
public class RestauranteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String culinaria;

    private Integer capacidade;

    @Column(name = "horario_abertura")
    private LocalTime horarioAbertura;

    @Column(name = "horario_encerramento")
    private LocalTime horarioEncerramento;

    private Integer ddd;

    private Long telefone;

    @OneToOne(mappedBy = "restaurante")
    private EnderecoModel endereco;

    @OneToMany(mappedBy = "restaurante")
    private List<MesaModel> mesas;

    @OneToMany(mappedBy = "restaurante")
    private List<ReservaModel> reservas;

}
