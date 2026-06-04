package edu.ut.convocatoria.domain.dto.response;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estructura estandarizada para el retorno de errores de la API.")
public record ErrorResponseDTO(
        @Schema(description = "Código interno del error.", example = "VALIDATION_ERROR")
        String code,
        @Schema(description = "Mensaje detallado explicando el error.", example = "El nombre no puede estar vacío, el stock debe ser mayor a cero.")
        String message,
        @Schema(description = "Estado HTTP devuelto.", example = "BAD_REQUEST")
        HttpStatus status
) {
}
