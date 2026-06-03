package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.request.BranchRequestDTO;
import edu.ut.convocatoria.domain.dto.request.FranchiseRequestDTO;
import edu.ut.convocatoria.domain.dto.response.BranchResponseDTO;
import edu.ut.convocatoria.domain.dto.response.ErrorResponseDTO;
import edu.ut.convocatoria.domain.dto.response.FranchiseResponseDTO;
import edu.ut.convocatoria.domain.dto.response.MaxStockResponseDTO;
import edu.ut.convocatoria.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/franchises")
@Tag(name = "Franquicias y sucursales", description = "Endpoints para administrar las franquicias y las sucursales que pertenecen a estas.")
public class FranchiseController {
    private final FranchiseService franchiseService;

    @PostMapping
    @Operation(
            summary = "Añadir una nueva franquicia.",
            description = "Crea una franquicia nueva en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Franquicia creada con exito.",
                    content = @Content(schema = @Schema(implementation = FranchiseResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Datos de entrada invalidos.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente o invalido.", content = @Content)
    })
    public ResponseEntity<FranchiseResponseDTO> createFranchise(
            @Valid @RequestBody FranchiseRequestDTO franchiseRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(franchiseService.createFranchise(franchiseRequestDTO));
    }


    @PostMapping("/{franchiseId}/branches")
    @Operation(
            summary = "Agregar una sucursal a una franquicia",
            description = "Crea una sucursal fisica y la vincula a la franquicia especificada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Sucursal creada y vinculada con éxito",
                    content = @Content(schema = @Schema(implementation = BranchResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Datos de la sucursal inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente o invalido.", content = @Content),
            @ApiResponse(
                    responseCode = "404", description = "La franquicia proporcionada no existe",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    public ResponseEntity<BranchResponseDTO> createBranch(
            @Valid @NotNull @PathVariable("franchiseId") UUID franchiseId,
            @Valid @RequestBody BranchRequestDTO branchRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(franchiseService.createBranch(franchiseId, branchRequestDTO));
    }


    @GetMapping("/{franchiseId}/products/max-stock")
    @Operation(
            summary = "Obtener productos con mayor stock por sucursal",
            description = "Obtiene un listado de los productos con mayor stock para cada una de las sucursales asociadas a la franquicia especificada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Consulta realizada de manera exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MaxStockResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente o invalido.", content = @Content),
            @ApiResponse(
                    responseCode = "404", description = "La franquicia indicada no fue encontrada en el sistema",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })

    public ResponseEntity<List<MaxStockResponseDTO>> getMaxStock(
            @Valid @NotNull @PathVariable("franchiseId") UUID franchiseId
    ) {
        return ResponseEntity.ok(franchiseService.getMaxStock(franchiseId));
    }


}
