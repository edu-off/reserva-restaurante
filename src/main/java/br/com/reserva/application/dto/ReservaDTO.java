package br.com.reserva.application.dto;

import br.com.reserva.domain.enums.StatusReserva;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "o campo status não pode ser nulo")
    @NotEmpty(message = "o campo status não pode ser vazio")
    private String status;

    @NotNull(message = "o campo agendamento não pode ser nulo")
    private LocalDateTime agendamento;

    @NotNull(message = "o campo quantidade de pessoas não pode ser nulo")
    @Positive(message = "o campo quantidade de pessoas nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo quantidade de pessoas não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer quantidadePessoas;

    @Valid
    @NotNull(message = "objeto restaurante não pode ser nulo")
    private RestauranteDTO restaurante;

    @Valid
    @NotNull(message = "objeto cliente não pode ser nulo")
    private ClienteDTO cliente;

    private AvaliacaoDTO avaliacao;

}
