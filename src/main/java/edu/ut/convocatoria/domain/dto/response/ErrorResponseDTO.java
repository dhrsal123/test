package edu.ut.convocatoria.domain.dto.response;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(
        String code,
        String message,
        HttpStatus status
) {
}
