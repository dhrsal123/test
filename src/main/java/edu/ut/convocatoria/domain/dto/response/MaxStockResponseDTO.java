package edu.ut.convocatoria.domain.dto.response;


import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta que indica el producto con mayor stock para una sucursal específica.")
public record MaxStockResponseDTO(
        @Schema(description = "Identificador de la sucursal.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID branchId,

        @Schema(description = "Datos del producto que tiene el mayor nivel de stock en la sucursal.")
        ProductResponseDTO maxStockProduct
) {
}
