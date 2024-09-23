package br.com.reserva.domain.entities;

import br.com.reserva.domain.objectsvalue.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.reserva.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class Restaurante {

    private Long id;
    private String nome;
    private String culinaria;
    private Integer capacidade;
    private LocalTime horarioAbertura;
    private LocalTime horarioEncerramento;
    private Integer ddd;
    private Long telefone;
    private Endereco endereco;
    private List<Mesa> mesas;
    private List<Reserva> reservas;

    public Restaurante(String nome,
                       String culinaria,
                       Integer capacidade,
                       LocalTime horarioAbertura,
                       LocalTime horarioEncerramento,
                       Integer ddd,
                       Long telefone,
                       Endereco endereco) {
        if (!isValidString(nome))
            throw new IllegalArgumentException("nome inválido");
        if (!isValidString(culinaria))
            throw new IllegalArgumentException("culinária inválida");
        if (!isValidNumber(capacidade))
            throw new IllegalArgumentException("capacidade inválida");
        if (capacidade <= 0)
            throw new IllegalArgumentException("capacidade inferior ao permitido");
        if (capacidade > 1000)
            throw new IllegalArgumentException("capacidade superior ao permitido");
        if (!isValidObject(horarioAbertura) || !isValidObject(horarioEncerramento))
            throw new IllegalArgumentException("horario de funcionamento inválido");
        if (horarioAbertura.isAfter(horarioEncerramento))
            throw new IllegalArgumentException("horario de abertura não deve ser superior ao horário de encerramento");
        if (!isValidDdd(ddd))
            throw new IllegalArgumentException("ddd inválido");
        if (!isValidTelefone(telefone))
            throw new IllegalArgumentException("telefone inválido");
        if (!isValidObject(endereco))
            throw new IllegalArgumentException("endereço inválido");
        this.nome = nome;
        this.culinaria = culinaria;
        this.capacidade = capacidade;
        this.horarioAbertura = horarioAbertura;
        this.horarioEncerramento = horarioEncerramento;
        this.ddd = ddd;
        this.telefone = telefone;
        this.endereco = endereco;
        this.mesas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void adicionaReserva(Reserva reserva) {
        reservas.add(reserva);
    }

}
