package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String token,
        boolean active
) {
}
