package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.request.ProductRequestDTO;
import edu.ut.convocatoria.domain.dto.request.ProductStockRequestDTO;
import edu.ut.convocatoria.domain.dto.response.ProductResponseDTO;
import edu.ut.convocatoria.domain.dto.response.ProductStockResponseDTO;
import edu.ut.convocatoria.service.BranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/branches")
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/{branchId}/products")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @NotNull @PathVariable("branchId") UUID branchId,
            @Valid @RequestBody ProductRequestDTO productRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(branchService.createProduct(branchId, productRequestDTO));
    }

    @DeleteMapping("/{branchId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @Valid @NotNull @PathVariable("branchId") UUID branchId,
            @Valid @NotNull @PathVariable("productId") UUID productId
    ) {
        branchService.deleteProduct(branchId, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{branchId}/products/{productId}/stock")
    public ResponseEntity<ProductStockResponseDTO> updateStock(
            @Valid @NotNull @PathVariable("branchId") UUID branchId,
            @Valid @NotNull @PathVariable("productId") UUID productId,
            @Valid @RequestBody ProductStockRequestDTO productStockRequestDTO
    ) {
        return ResponseEntity.ok(branchService.updateStock(branchId, productId, productStockRequestDTO));

    }
}
