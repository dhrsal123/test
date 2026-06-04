package edu.ut.convocatoria.domain.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos requeridos para la creación de un nuevo producto en el inventario.")
public record ProductRequestDTO(
        @Schema(description = "Nombre del producto.", example = "Hamburguesa de cordero.")
        @NotEmpty
        @Size(min = 10, max = 100)
        String name,

        @Schema(description = "Código SKU único del producto.", example = "MCD-COD-001")
        @NotEmpty
        @Size(min = 3, max = 50)
        String sku,

        @Schema(description = "Descripción detallada del producto.", example = "Deliciosa hamburguesa de cordero.")
        @NotEmpty
        @Size(min = 10, max = 512)
        String description,

        @Schema(description = "Precio unitario del producto.", example = "5.99")       
        @Positive
        Double price,

        @Schema(description = "Stock inicial disponible al crear el producto.", example = "50")
        @PositiveOrZero
        Integer stock
) {

}
