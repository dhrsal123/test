package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.request.ProductRequestDTO;
import edu.ut.convocatoria.domain.dto.request.ProductStockRequestDTO;
import edu.ut.convocatoria.domain.dto.response.ErrorResponseDTO;
import edu.ut.convocatoria.domain.dto.response.ProductResponseDTO;
import edu.ut.convocatoria.domain.dto.response.ProductStockResponseDTO;
import edu.ut.convocatoria.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sucursales e inventario", description = "Endpoints que permiten el manejo de los productos en las sucursales especificadas.")
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/{branchId}/products")
    @Operation(summary = "Añadir un nuevo producto a la sucursal.", description = "Permite añadir un producto al inventario global y asigna el inventario base en la sucursal indicada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto e inventario registrados de manera existosa."
                    , content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400", description = "Los datos de entrada estan malformados o son invalidos.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente o invalido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "La sucursal especificada no existe.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El producto ya se encuentra asociado a esta sucursal.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @NotNull @PathVariable("branchId") UUID branchId,
            @Valid @RequestBody ProductRequestDTO productRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(branchService.createProduct(branchId, productRequestDTO));
    }

    @DeleteMapping("/{branchId}/products/{productId}")
    @Operation(
            summary = "Eliminar un producto de una sucursal",
            description = "Elimina de forma logico o fisica el producto de la sucursal."
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto desasociado y eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Identificadores UUID invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente o invalido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "El producto o la sucursal no fueron encontrados.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteProduct(
            @Valid @NotNull @PathVariable("branchId") UUID branchId,
            @Valid @NotNull @PathVariable("productId") UUID productId
    ) {
        branchService.deleteProduct(branchId, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{branchId}/products/{productId}/stock")
    @Operation(
            summary = "Modificar el stock de un producto",
            description = "Actualiza las existencias disponibles de un producto específico dentro de una sucursal."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente", content = @Content(schema = @Schema(implementation = ProductStockResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de stock inválidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente o invalido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "La relación de producto y sucursal no existe", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<ProductStockResponseDTO> updateStock(
            @Valid @NotNull @PathVariable("branchId") UUID branchId,
            @Valid @NotNull @PathVariable("productId") UUID productId,
            @Valid @RequestBody ProductStockRequestDTO productStockRequestDTO
    ) {
        return ResponseEntity.ok(branchService.updateStock(branchId, productId, productStockRequestDTO));

    }
}
