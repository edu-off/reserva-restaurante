package br.com.reserva.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "o campo titulo não pode ser nulo")
    @NotEmpty(message = "o campo titulo não pode ser vazio")
    private String titulo;

    @NotNull(message = "o campo nota não pode ser nulo")
    @Positive(message = "o campo nota nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo nota de pessoas não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer nota;

    @NotNull(message = "o campo comentario não pode ser nulo")
    @NotEmpty(message = "o campo comentario não pode ser vazio")
    private String comentario;

}
