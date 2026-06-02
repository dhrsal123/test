package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.response.ErrorResponseDTO;
import edu.ut.convocatoria.domain.enumerated.ExceptionTypes;
import edu.ut.convocatoria.domain.exceptions.ConvocatoriaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Unhandled exception occurred: ", e);

        var error = new ErrorResponseDTO(
                "INTERNAL_SERVER_ERROR",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(ConvocatoriaException.class)
    public ResponseEntity<ErrorResponseDTO> handleConvocatoriaException(ConvocatoriaException e) {
        var exceptionType = e.getExceptionType();
        log.warn("Business exception [{}]: {}", exceptionType.getCode(), e.getMessage());

        var error = new ErrorResponseDTO(
                exceptionType.getCode(),
                e.getMessage(),
                exceptionType.getStatus()
        );

        return ResponseEntity.status(exceptionType.getStatus()).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
        String detailedMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Validation failed: {}", detailedMessage);

        var error = new ErrorResponseDTO(
                "VALIDATION_ERROR",
                detailedMessage,
                HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Class<?> requiredType = e.getRequiredType();
        if (Objects.isNull(requiredType)) {
            requiredType = Object.class;
        }

        String message = String.format("Parameter '%s' should be of type %s", e.getName(), requiredType.getSimpleName());
        log.warn("Type mismatch error: {}", message);

        var error = new ErrorResponseDTO(
                "BAD_REQUEST",
                message,
                HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("Access denied: {}", e.getMessage());
        var forbidden = ExceptionTypes.FORBIDDEN;

        var error = new ErrorResponseDTO(
                forbidden.getCode(),
                e.getMessage(),
                forbidden.getStatus()
        );

        return ResponseEntity.status(forbidden.getStatus()).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());
        var noauth = ExceptionTypes.UNAUTHORIZED;

        var error = new ErrorResponseDTO(
                noauth.getCode(),
                e.getMessage(),
                noauth.getStatus()
        );

        return ResponseEntity.status(noauth.getStatus()).body(error);
    }
}
