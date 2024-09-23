package br.com.reserva.domain.objectsvalue;

import br.com.reserva.domain.entities.Restaurante;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.reserva.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class Endereco {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private Long cep;
    private Restaurante restaurante;

    public Endereco(String logradouro, String numero, String bairro, String cidade, String uf, Long cep) {
        if (!isValidString(logradouro))
            throw new IllegalArgumentException("logradouro inválido");
        if (!isValidString(numero))
            throw new IllegalArgumentException("número inválido");
        if (!isValidString(bairro))
            throw new IllegalArgumentException("bairro inválido");
        if (!isValidString(cidade))
            throw new IllegalArgumentException("cidade inválida");
        if (!isValidString(uf) || (!isValidUf(uf)))
            throw new IllegalArgumentException("uf inválida");
        if (!isValidNumber(cep) || !isValidCep(cep))
            throw new IllegalArgumentException("cep inválido");
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

}
