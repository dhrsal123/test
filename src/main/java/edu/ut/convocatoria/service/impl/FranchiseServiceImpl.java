package edu.ut.convocatoria.service.impl;

import edu.ut.convocatoria.domain.dto.request.BranchRequestDTO;
import edu.ut.convocatoria.domain.dto.request.FranchiseRequestDTO;
import edu.ut.convocatoria.domain.dto.response.BranchResponseDTO;
import edu.ut.convocatoria.domain.dto.response.FranchiseResponseDTO;
import edu.ut.convocatoria.domain.dto.response.MaxStockResponseDTO;
import edu.ut.convocatoria.domain.enumerated.ExceptionTypes;
import edu.ut.convocatoria.domain.exceptions.ConvocatoriaException;
import edu.ut.convocatoria.mapper.BranchMapper;
import edu.ut.convocatoria.mapper.FranchiseMapper;
import edu.ut.convocatoria.mapper.ProductBranchMapper;
import edu.ut.convocatoria.repository.BranchRepository;
import edu.ut.convocatoria.repository.FranchiseRepository;
import edu.ut.convocatoria.repository.ProductBranchRepository;
import edu.ut.convocatoria.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {
    private final FranchiseMapper franchiseMapper;
    private final BranchMapper branchMapper;
    private final ProductBranchMapper productBranchMapper;

    private final BranchRepository branchRepository;

    private final FranchiseRepository franchiseRepository;

    private final ProductBranchRepository productBranchRepository;

    @Override
    public FranchiseResponseDTO createFranchise(FranchiseRequestDTO franchiseRequestDTO) {
        var entity = franchiseMapper.toEntity(franchiseRequestDTO);
        var resp = franchiseRepository.save(entity);

        return franchiseMapper.toDto(resp);
    }

    @Override
    public BranchResponseDTO createBranch(UUID franchiseId, BranchRequestDTO branchRequestDTO) {

        var franchiseEntity = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ConvocatoriaException(
                        "The franchise was not found.",
                        ExceptionTypes.NOT_FOUND
                ));

        var entity = branchMapper.toEntity(branchRequestDTO);
        entity.setFranchise(franchiseEntity);

        var resp = branchRepository.save(entity);

        var branches = franchiseEntity.getBranches();
        branches.add(resp);

        franchiseEntity.setBranches(branches);
        franchiseRepository.save(franchiseEntity);

        return branchMapper.toDto(resp);
    }

    @Override
    public List<MaxStockResponseDTO> getMaxStock(UUID franchiseId) {

        var response = productBranchRepository.findMaxStockByFranchiseId(franchiseId)
                .orElseThrow(() -> new ConvocatoriaException(
                        "The franchise was not found.",
                        ExceptionTypes.NOT_FOUND
                ));

        return response.stream()
                .map(productBranchMapper::toMaxStockDto)
                .toList();
    }
}
