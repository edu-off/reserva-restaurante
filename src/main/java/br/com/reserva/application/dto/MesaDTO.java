package br.com.reserva.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "o campo capacidade não pode ser nulo")
    @Positive(message = "o campo capacidade nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo capacidade não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer capacidade;

}
