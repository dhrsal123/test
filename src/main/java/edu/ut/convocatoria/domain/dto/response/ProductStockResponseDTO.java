package edu.ut.convocatoria.domain.dto.response;

import java.util.UUID;

public record ProductStockResponseDTO(
        UUID id,
        String name,
        String description,
        Double price,
        Integer stock
) {
}
