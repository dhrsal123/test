package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;

public record FranchiseResponseDTO(
        UUID id,
        String name,
        String description
) {
}
