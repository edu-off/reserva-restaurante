package br.com.reserva.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @NotNull(message = "o campo email não pode ser nulo")
    @NotEmpty(message = "o campo email não pode ser vazio")
    private String email;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo ddd não pode ser nulo")
    @Positive(message = "o campo ddd nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo ddd não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer ddd;

    @NotNull(message = "o campo telefone não pode ser nulo")
    @Positive(message = "o campo telefone nao pode ser zero ou menor que zero")
    @Digits(integer = 9, fraction = 0, message = "o campo telefone não pode ter casas decimais e não pode ter mais que 9 dígitos")
    private Long telefone;

}
