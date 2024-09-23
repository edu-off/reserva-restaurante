package br.com.reserva.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteDTO {

    private Long id;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo culinária não pode ser nulo")
    @NotEmpty(message = "o campo culinária não pode ser vazio")
    private String culinaria;

    @NotNull(message = "o campo capacidade não pode ser nulo")
    @Positive(message = "o campo capacidade nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo capacidade não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer capacidade;

    @NotNull(message = "o campo horario de abertura não pode ser nulo")
    private LocalTime horarioAbertura;

    @NotNull(message = "o campo horario de encerramento não pode ser nulo")
    private LocalTime horarioEncerramento;

    @NotNull(message = "o campo ddd não pode ser nulo")
    @Positive(message = "o campo ddd nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo ddd não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer ddd;

    @NotNull(message = "o campo telefone não pode ser nulo")
    @Positive(message = "o campo telefone nao pode ser zero ou menor que zero")
    @Digits(integer = 9, fraction = 0, message = "o campo telefone não pode ter casas decimais e não pode ter mais que 9 dígitos")
    private Long telefone;

    @Valid
    @NotNull(message = "objeto endereco não pode ser nulo")
    private EnderecoDTO endereco;

}
