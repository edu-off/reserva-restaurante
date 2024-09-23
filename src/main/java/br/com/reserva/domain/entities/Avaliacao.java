package br.com.reserva.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.reserva.domain.utils.Validator.isValidNumber;
import static br.com.reserva.domain.utils.Validator.isValidString;

@Getter
@Setter
@NoArgsConstructor
public class Avaliacao {

    private Long id;
    private String titulo;
    private Integer nota;
    private String comentario;
    private Reserva reserva;

    public Avaliacao(String titulo, Integer nota, String comentario) {
        if (!isValidString(titulo))
           throw new IllegalArgumentException("título inválido");
        if (!isValidNumber(nota))
            throw new IllegalArgumentException("nota inválida");
        if (nota <= 0)
            throw new IllegalArgumentException("nota inferior ao permitido");
        if (nota > 10)
            throw new IllegalArgumentException("nota superior ao permitido");
        if (!isValidString(comentario))
            throw new IllegalArgumentException("comentário inválido");

        this.titulo = titulo;
        this.nota = nota;
        this.comentario = comentario;
    }

}
