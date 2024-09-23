package br.com.reserva.infrastructure.database.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "endereco")
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String uf;

    private Long cep;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id")
    private RestauranteModel restaurante;

}
