package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los datos de una franquicia registrada.")
public record FranchiseResponseDTO(
        @Schema(description = "Identificador único de la franquicia.", example = "123e4567-e89b-12d3-a456-426614174001")
        UUID id,
        @Schema(description = "Nombre de la franquicia.", example = "MacDonald's")
        String name,

        @Schema(description = "Descripción de la franquicia.", example = "Empresa dedicada a la distribución de hamburguesas y productos de comida rapida.")
        String description
) {
}
