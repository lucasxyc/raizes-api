package br.com.raizes.raizesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("error", "REQUISICAO_INVALIDA");
        body.put("message", "Existem erros de validação nos campos enviados.");
        body.put("path", request.getRequestURI());

        List<Map<String, String>> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> detail = new HashMap<>();
            detail.put("field", error.getField());
            detail.put("issue", error.getDefaultMessage());
            details.add(detail);
        }
        body.put("details", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());

        String errorName = ex.getStatusCode() == HttpStatus.CONFLICT ? "CONFLITO_REGRA_NEGOCIO" : "RECURSO_NAO_ENCONTRADO";
        body.put("error", errorName);
        body.put("message", ex.getReason());
        body.put("details", List.of());
        body.put("path", request.getRequestURI());

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericErrors(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("error", "ERRO_INTERNO_SERVIDOR");
        body.put("message", ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado no sistema.");
        body.put("details", List.of());
        body.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex, jakarta.servlet.http.HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", java.time.Instant.now().toString());
        body.put("error", "ACESSO_NEGADO");
        body.put("message", "Você não tem permissão para acessar este recurso.");
        body.put("details", List.of());
        body.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

}
