package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;

public record BranchResponseDTO(
        UUID id,
        String name,
        String description,
        String address
) {
}
