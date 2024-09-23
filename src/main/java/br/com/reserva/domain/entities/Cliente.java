package br.com.reserva.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.reserva.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    private String email;
    private String nome;
    private Integer ddd;
    private Long telefone;
    private List<Reserva> reservas = new ArrayList<>();

    public Cliente(String email, String nome, Integer ddd, Long telefone) {
        if (!isValidEmail(email))
            throw new IllegalArgumentException("email inválido");
        if (!isValidString(nome))
            throw new IllegalArgumentException("nome inválido");
        if (!Objects.isNull(ddd)) {
            if (!isValidDdd(ddd))
                throw new IllegalArgumentException("ddd inválido");
        }
        if (!Objects.isNull(telefone)) {
            if (!isValidTelefone(telefone))
                throw new IllegalArgumentException("telefone inválido");
        }
        this.email = email;
        this.nome = nome;
        this.ddd = ddd;
        this.telefone = telefone;
    }

    public void adicionaReserva(Reserva reserva) {
        reservas.add(reserva);
    }

}
