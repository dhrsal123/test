package edu.ut.convocatoria.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaxStockProductProjection {
    private UUID branchId;
    private UUID productId;
    private String name;
    private String sku;
    private String description;
    private Double price;
    private Integer stock;
}
