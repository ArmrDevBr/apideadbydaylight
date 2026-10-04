package br.com.armrdev.apideadbydaylight.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Modelo padronizado para respostas de erro da API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<ValidationErrorDetail> validationErrors
) {
    public record ValidationErrorDetail(String field, String message) {}

    // Factory method para erros simples
    public static StandardError of(Integer status, String error, String message, String path) {
        return new StandardError(Instant.now(), status, error, message, path, null);
    }

    // Factory method para erros com detalhes de validação (@NotBlank, @NotNull, etc.)
    public static StandardError ofValidation(Integer status, String error, String message, String path, List<ValidationErrorDetail> errors) {
        return new StandardError(Instant.now(), status, error, message, path, errors);
    }
}
