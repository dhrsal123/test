package edu.ut.convocatoria.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos requeridos para la creación de una nueva sucursal.")
public record BranchRequestDTO(
        @Schema(description = "Nombre de la sucursal.", example = "Sucursal MacDonalds El Poblado")
        @NotEmpty
        @Size(min = 10, max = 100)
        String name,

        @Schema(description = "Descripción detallada de la sucursal.", example = "Sucursal principal ubicada en el centro de la ciudad.")
        @NotEmpty
        @Size(min = 10, max = 512)
        String description,

        @Schema(description = "Dirección física de la sucursal.", example = "Calle 10 # 5-50, Centro")
        @NotEmpty
        @Size(min = 10, max = 256)
        String address
) {
}
