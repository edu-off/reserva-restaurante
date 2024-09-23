package br.com.reserva.domain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.reserva.domain.utils.Validator.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Nested
    public class ValidacaoEmail {

        @Test
        @DisplayName("Deve validar e-mails com estrutura correta")
        public void deveValidarEmailsComEstruturaCorreta() {
            assertThat(isValidEmail("test@test.com")).isTrue();
            assertThat(isValidEmail("test@test.com.br")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar e-mails com estrutura inccorreta")
        public void deveInvalidarEmailsComEstruturaIncorreta() {
            assertThat(isValidEmail("test")).isFalse();
            assertThat(isValidEmail("test@")).isFalse();
            assertThat(isValidEmail("@test")).isFalse();
            assertThat(isValidEmail("@test.com")).isFalse();
            assertThat(isValidEmail("@test.com.br")).isFalse();
            assertThat(isValidEmail("test.com")).isFalse();
            assertThat(isValidEmail("@test.com.br")).isFalse();
            assertThat(isValidEmail("@")).isFalse();
            assertThat(isValidEmail("test@test")).isFalse();
        }

    }

    @Nested
    public class ValidacaoStrings {

        @Test
        @DisplayName("Deve validar strings validas")
        public void deveValidarStringsValidas() {
            assertThat(isValidString("1")).isTrue();
            assertThat(isValidString("test")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar strings invalidas")
        public void deveInvalidarStringsInvalidas() {
            assertThat(isValidString(null)).isFalse();
            assertThat(isValidString("")).isFalse();
            assertThat(isValidString("    ")).isFalse();
        }

    }

    @Nested
    public class ValidacaoNumeros {

        @Test
        @DisplayName("Deve validar objetos de numeros")
        public void deveValidarObjetosDeNumeros() {
            assertThat(isValidNumber(Short.valueOf("1"))).isTrue();
            assertThat(isValidNumber(Integer.valueOf("1"))).isTrue();
            assertThat(isValidNumber(Long.valueOf("1"))).isTrue();
            assertThat(isValidNumber(BigDecimal.valueOf(1L))).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar objetos que não de numeros")
        public void deveInvalidarObjetosQueNaoSaoDeNumeros() {
            assertThat(isValidNumber(null)).isFalse();
            assertThat(isValidNumber("")).isFalse();
            assertThat(isValidNumber("    ")).isFalse();
        }

    }

    @Nested
    public class ValidacaoObjetos {

        @Test
        @DisplayName("Deve validar objetos nao nulos")
        public void deveValidarObjetosNaoNulos() {
            assertThat(isValidObject("1")).isTrue();
            assertThat(isValidObject("")).isTrue();
            assertThat(isValidObject(" ")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar objetos nulos")
        public void deveInvalidarObjetosQueNaoSaoDeNumeros() {
            assertThat(isValidObject(null)).isFalse();
        }

    }

    @Nested
    public class ValidacaoUf {

        @Test
        @DisplayName("Deve validar ufs validas")
        public void deveValidarUfsValidas() {
            assertThat(isValidUf("SP")).isTrue();
            assertThat(isValidUf("RJ")).isTrue();
            assertThat(isValidUf("MG")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar ufs invalidas")
        public void deveInvalidarUfsInvalidas() {
            assertThat(isValidUf("sp")).isFalse();
            assertThat(isValidUf("rj")).isFalse();
            assertThat(isValidUf("mg")).isFalse();
            assertThat(isValidUf("XX")).isFalse();
            assertThat(isValidUf("  ")).isFalse();
            assertThat(isValidUf("")).isFalse();
        }

    }

    @Nested
    public class ValidacaoDdd {

        @Test
        @DisplayName("Deve validar ddds validos")
        public void deveValidarDddsValidos() {
            assertThat(isValidDdd(1)).isTrue();
            assertThat(isValidDdd(99)).isTrue();
        }

        @Test
        @DisplayName("Deve validar ddds invalidos")
        public void deveValidarDddsInvalidos() {
            assertThat(isValidDdd(null)).isFalse();
            assertThat(isValidDdd(0)).isFalse();
            assertThat(isValidDdd(100)).isFalse();
        }

    }

    @Nested
    public class ValidacaoTelefone {

        @Test
        @DisplayName("Deve validar telefone validos")
        public void deveValidarTelefonesValidos() {
            assertThat(isValidTelefone(1L)).isTrue();
            assertThat(isValidTelefone(999999999L)).isTrue();
        }

        @Test
        @DisplayName("Deve validar telefone invalidos")
        public void deveValidarTelefonesInvalidos() {
            assertThat(isValidTelefone(null)).isFalse();
            assertThat(isValidTelefone(0L)).isFalse();
            assertThat(isValidTelefone(1000000000L)).isFalse();
        }

    }

    @Nested
    public class ValidacaoCep {

        @Test
        @DisplayName("Deve validar ceps validos")
        public void deveValidarCepsValidos() {
            assertThat(isValidCep(1L)).isTrue();
            assertThat(isValidCep(99999999L)).isTrue();
        }

        @Test
        @DisplayName("Deve validar ceps invalidos")
        public void deveValidarCepsInvalidos() {
            assertThat(isValidCep(null)).isFalse();
            assertThat(isValidCep(0L)).isFalse();
            assertThat(isValidCep(100000000L)).isFalse();
        }

    }

}
