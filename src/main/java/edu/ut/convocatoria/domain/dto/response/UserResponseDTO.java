package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación o registro de usuario.")
public record UserResponseDTO(
        @Schema(description = "Identificador único del usuario.", example = "123e4567-e89b-12d3-a456-426614174003")
        UUID id,
        @Schema(description = "Token JWT generado para las futuras peticiones.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,
        @Schema(description = "Estado de la cuenta del usuario.", example = "true")
        boolean active
) {
}
