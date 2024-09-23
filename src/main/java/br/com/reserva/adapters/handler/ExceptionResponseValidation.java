package br.com.reserva.adapters.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseValidation extends ExceptionResponse {

    private List<FieldMessage> errors;

    public ExceptionResponseValidation(String timestamp, Integer status, String message, String path) {
        super(timestamp, status, message, path);
        this.errors = new ArrayList<>();
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

}
