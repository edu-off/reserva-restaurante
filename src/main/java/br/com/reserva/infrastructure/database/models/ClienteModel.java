package br.com.reserva.infrastructure.database.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class ClienteModel {

    @Id
    private String email;

    private String nome;

    private Integer ddd;

    private Long telefone;

    @OneToMany(mappedBy = "cliente")
    private List<ReservaModel> reservas = new ArrayList<>();

}
