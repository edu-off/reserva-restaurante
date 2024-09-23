package br.com.reserva.domain.entities;

import br.com.reserva.domain.enums.StatusMesa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.reserva.domain.utils.Validator.isValidNumber;
import static br.com.reserva.domain.utils.Validator.isValidObject;

@Getter
@Setter
@NoArgsConstructor
public class Mesa {

    private Long id;
    private Integer capacidade;
    private StatusMesa status;
    private Restaurante restaurante;

    public Mesa(Integer capacidade, StatusMesa status) {
        if (!isValidNumber(capacidade))
            throw new IllegalArgumentException("capacidade inválida");
        if (capacidade <= 0)
            throw new IllegalArgumentException("capacidade inferior ao permitido");
        if (capacidade > 10)
            throw new IllegalArgumentException("capacidade superior ao permitido");
        if (!isValidObject(status))
            throw new IllegalArgumentException("status inválido");
        this.capacidade = capacidade;
        this.status = status;
    }

}
