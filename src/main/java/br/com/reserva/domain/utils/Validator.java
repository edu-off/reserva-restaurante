package br.com.reserva.domain.utils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final List<String> ufs = List.of("RO", "AC", "AM", "RR", "PA", "AP", "TO", "MA", "PI", "CE", "RN", "PB", "PE", "AL", "SE", "BA", "MG", "ES", "RJ", "SP", "PR", "SC", "RS", "MS", "MT", "GO", "DF");

    public static boolean isValidString(String value) {
        return (Objects.nonNull(value) && !value.isBlank());
    }

    public static boolean isValidNumber(Object value) {
        return (Objects.nonNull(value) && value instanceof Number);
    }

    public static boolean isValidObject(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean isValidUf(String uf) {
        return ufs.contains(uf);
    }

    public static boolean isValidEmail(String email) {
        if (!isValidString(email))
            return false;
        return VALID_EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidDdd(Integer ddd) {
        if (!isValidNumber(ddd))
            return false;
        return ddd > 0 && ddd <= 99;
    }

    public static boolean isValidTelefone(Long telefone) {
        if (!isValidNumber(telefone))
            return false;
        return telefone > 0 && telefone <= 999999999;
    }

    public static boolean isValidCep(Long cep) {
        if (!isValidNumber(cep))
            return false;
        return cep > 0 && cep <= 99999999;
    }

}
