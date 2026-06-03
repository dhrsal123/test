package edu.ut.convocatoria.service;

import edu.ut.convocatoria.domain.dto.request.BranchRequestDTO;
import edu.ut.convocatoria.domain.dto.request.FranchiseRequestDTO;
import edu.ut.convocatoria.domain.dto.response.BranchResponseDTO;
import edu.ut.convocatoria.domain.dto.response.FranchiseResponseDTO;
import edu.ut.convocatoria.domain.dto.response.MaxStockResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FranchiseService {

    FranchiseResponseDTO createFranchise(FranchiseRequestDTO franchiseRequestDTO);

    BranchResponseDTO createBranch(UUID franchiseId, BranchRequestDTO branchRequestDTO);

    List<MaxStockResponseDTO> getMaxStock(UUID franchiseId);

}
