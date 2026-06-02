package edu.ut.convocatoria.service;

import edu.ut.convocatoria.domain.dto.request.ProductRequestDTO;
import edu.ut.convocatoria.domain.dto.request.ProductStockRequestDTO;
import edu.ut.convocatoria.domain.dto.response.ProductResponseDTO;
import edu.ut.convocatoria.domain.dto.response.ProductStockResponseDTO;

import java.util.UUID;

public interface BranchService {

    ProductResponseDTO createProduct(
            UUID branchId,
            ProductRequestDTO productRequestDTO
    );

    void deleteProduct(
            UUID branchId,
            UUID productId
    );

    ProductStockResponseDTO updateStock(
            UUID branchId,
            UUID productId,
            ProductStockRequestDTO productStockRequestDTO
    );
}
