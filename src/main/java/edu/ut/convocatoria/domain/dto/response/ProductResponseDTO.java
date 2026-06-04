package edu.ut.convocatoria.domain.dto.response;


import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los detalles de un producto en el inventario.")
public record ProductResponseDTO(
        @Schema(description = "Identificador único del producto.", example = "123e4567-e89b-12d3-a456-426614174002")
        UUID id,
        @Schema(description = "Código SKU único del producto.", example = "MCD-COD-001")
        String sku,
        @Schema(description = "Nombre del producto.", example = "Hamburguesa de cordero.")
        String name,
        @Schema(description = "Descripción detallada del producto.", example = "Deliciosa hamburguesa de cordero.")
        String description,
        @Schema(description = "Precio unitario del producto.", example = "5.99")       
        Double price,
        @Schema(description = "Cantidad de stock disponible.", example = "50")
        Integer stock
) {

}
