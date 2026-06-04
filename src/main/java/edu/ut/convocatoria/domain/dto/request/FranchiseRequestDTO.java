package edu.ut.convocatoria.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos requeridos para el registro de una nueva franquicia.")
public record FranchiseRequestDTO(
        @Schema(description = "Nombre de la franquicia.", example = "MacDonald's")
        @NotEmpty
        @Size(min = 10, max = 100)
        String name,

        @Schema(description = "Descripción de la franquicia.", example = "Empresa dedicada a la distribución de hamburguesas y productos de comida rapida.")
        @NotEmpty
        @Size(min = 10, max = 512)
        String description
) {
}
