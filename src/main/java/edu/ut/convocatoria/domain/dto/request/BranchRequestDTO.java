package edu.ut.convocatoria.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record BranchRequestDTO(
        @NotEmpty
        @Size(min = 10, max = 100)
        String name,

        @NotEmpty
        @Size(min = 10, max = 512)
        String description,

        @NotEmpty
        @Size(min = 10, max = 256)
        String address
) {
}
