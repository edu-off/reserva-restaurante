package br.com.reserva.domain.entities;

import br.com.reserva.domain.enums.StatusReserva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static br.com.reserva.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    private Long id;
    private StatusReserva status;
    private LocalDateTime agendamento;
    private Integer quantidadePessoas;
    private Restaurante restaurante;
    private Cliente cliente;
    private Mesa mesa;
    private Avaliacao avaliacao;


    public Reserva(StatusReserva status, LocalDateTime agendamento, Integer quantidadePessoas, Restaurante restaurante, Mesa mesa, Cliente cliente) {
        if (!isValidObject(status))
            throw new IllegalArgumentException("status inválido");
        if (!isValidObject(agendamento))
            throw new IllegalArgumentException("horário de agendamento inválido");
        if (agendamento.isBefore(LocalDateTime.now().plusHours(6)))
            throw new IllegalArgumentException("data e hora de agendamento não deve possuir intervalo inferior 6 horas do instante da reserva");
        if (agendamento.isAfter(LocalDateTime.now().plusMonths(3)))
            throw new IllegalArgumentException("data e hora de agendamento não deve ser superior a 3 meses");
        if (!isValidNumber(quantidadePessoas))
            throw new IllegalArgumentException("quantidade de pessoas inválida");
        if (quantidadePessoas <= 0)
            throw new IllegalArgumentException("quantidade de pessoas inferior ao permitido");
        if (quantidadePessoas > 100)
            throw new IllegalArgumentException("quantidade de pessoas superior ao permitido");
        if (!isValidObject(restaurante) || !isValidObject(restaurante.getId()))
            throw new IllegalArgumentException("restaurante inválido");
        if (!isValidObject(mesa) || !isValidObject(mesa.getId()))
            throw new IllegalArgumentException("mesa inválida");
        if (!isValidObject(cliente) || !isValidString(cliente.getEmail()))
            throw new IllegalArgumentException("cliente inválido");
        this.status = status;
        this.agendamento = agendamento;
        this.quantidadePessoas = quantidadePessoas;
        this.restaurante = restaurante;
        this.mesa = mesa;
        this.cliente = cliente;
    }

}
