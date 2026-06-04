package edu.ut.convocatoria.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos requeridos para la actualización de las existencias de un producto.")
public record ProductStockRequestDTO(
        @Schema(description = "Nueva cantidad de stock disponible.", example = "150")
        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock
) {

}
