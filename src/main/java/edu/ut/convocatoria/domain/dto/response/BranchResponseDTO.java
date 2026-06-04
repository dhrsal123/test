package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los datos de una sucursal registrada.")
public record BranchResponseDTO(
        @Schema(description = "Identificador único de la sucursal.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nombre de la sucursal.", example = "Sucursal MacDonalds El Poblado")
        String name,

        @Schema(description = "Descripción de la sucursal.", example = "Sucursal principal ubicada en el centro de la ciudad.")
        String description,

        @Schema(description = "Dirección física.", example = "Calle 10 # 5-50, Centro")
        String address
) {
}
