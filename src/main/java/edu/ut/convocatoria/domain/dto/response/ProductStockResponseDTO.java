package edu.ut.convocatoria.domain.dto.response;

public record ProductStockResponseDTO(
        String name,
        String description,
        Double price,
        Integer stock
) {
}
