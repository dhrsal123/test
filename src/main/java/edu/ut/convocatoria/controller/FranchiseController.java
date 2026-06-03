package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.request.BranchRequestDTO;
import edu.ut.convocatoria.domain.dto.request.FranchiseRequestDTO;
import edu.ut.convocatoria.domain.dto.response.BranchResponseDTO;
import edu.ut.convocatoria.domain.dto.response.FranchiseResponseDTO;
import edu.ut.convocatoria.domain.dto.response.MaxStockResponseDTO;
import edu.ut.convocatoria.service.FranchiseService;
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
public class FranchiseController {
    private final FranchiseService franchiseService;

    @PostMapping
    public ResponseEntity<FranchiseResponseDTO> createFranchise(
            @Valid @RequestBody FranchiseRequestDTO franchiseRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(franchiseService.createFranchise(franchiseRequestDTO));
    }


    @PostMapping("/{franchiseId}/branches")
    public ResponseEntity<BranchResponseDTO> createBranch(
            @Valid @NotNull @PathVariable("franchiseId") UUID franchiseId,
            @Valid @RequestBody BranchRequestDTO branchRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(franchiseService.createBranch(franchiseId, branchRequestDTO));
    }


    @GetMapping("/{franchiseId}/products/max-stock")
    public ResponseEntity<List<MaxStockResponseDTO>> getMaxStock(
            @Valid @NotNull @PathVariable("franchiseId") UUID franchiseId
    ) {
        return ResponseEntity.ok(franchiseService.getMaxStock(franchiseId));
    }


}
