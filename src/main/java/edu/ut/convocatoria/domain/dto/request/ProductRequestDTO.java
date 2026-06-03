package edu.ut.convocatoria.domain.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(
        @NotEmpty
        @Size(min = 10, max = 100)
        String name,

        @NotEmpty
        @Size(min = 3, max = 50)
        String sku,

        @NotEmpty
        @Size(min = 10, max = 512)
        String description,

        @Positive
        Double price,

        @PositiveOrZero
        Integer stock
) {

}
