package edu.ut.convocatoria.domain.dto.response;


import java.util.UUID;

public record MaxStockResponseDTO(
        UUID branchId,
        ProductResponseDTO maxStockProduct
) {
}
