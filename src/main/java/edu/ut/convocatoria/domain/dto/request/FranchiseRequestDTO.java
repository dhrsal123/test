package edu.ut.convocatoria.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record FranchiseRequestDTO(
        @NotEmpty
        @Size(min = 10, max = 100)
        String name,

        @NotEmpty
        @Size(min = 10, max = 512)
        String description
) {
}
