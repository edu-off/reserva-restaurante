package br.com.reserva.adapters.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private String timestamp;
    private Integer status;
    private String message;
    private String path;

}
