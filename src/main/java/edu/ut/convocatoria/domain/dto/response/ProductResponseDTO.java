package edu.ut.convocatoria.domain.dto.response;


import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String sku,
        String name,
        String description,
        Double price
) {

}
