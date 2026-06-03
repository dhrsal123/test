package edu.ut.convocatoria.service.impl;

import edu.ut.convocatoria.domain.dto.request.ProductRequestDTO;
import edu.ut.convocatoria.domain.dto.request.ProductStockRequestDTO;
import edu.ut.convocatoria.domain.dto.response.ProductResponseDTO;
import edu.ut.convocatoria.domain.entity.ProductBranchEntity;
import edu.ut.convocatoria.domain.entity.ProductEntity;
import edu.ut.convocatoria.domain.enumerated.ExceptionTypes;
import edu.ut.convocatoria.domain.exceptions.ConvocatoriaException;
import edu.ut.convocatoria.mapper.ProductBranchMapper;
import edu.ut.convocatoria.mapper.ProductMapper;
import edu.ut.convocatoria.repository.BranchRepository;
import edu.ut.convocatoria.repository.ProductBranchRepository;
import edu.ut.convocatoria.repository.ProductRepository;
import edu.ut.convocatoria.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    private final ProductBranchRepository productBranchRepository;

    private final ProductMapper productMapper;
    private final ProductBranchMapper productBranchMapper;

    @Override
    public ProductResponseDTO createProduct(UUID branchId, ProductRequestDTO productRequestDTO) {

        var branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ConvocatoriaException(
                        "The branch was not found.",
                        ExceptionTypes.NOT_FOUND
                ));

        var product = productRepository.findBySku(productRequestDTO.sku())
                .orElseGet(() -> {
                    var newProductEntity = productMapper.toEntity(productRequestDTO);
                    return productRepository.save(newProductEntity);
                });

        var alreadyExistsInBranch = productBranchRepository
                .findByBranchIdAndProductId(
                        branch.getId(),
                        product.getId()
                );

        if (alreadyExistsInBranch.isPresent()) {
            throw new ConvocatoriaException(
                    "This product is already associated with the branch.",
                    ExceptionTypes.BUSINESS_ERROR
            );
        }

        var productBranchEntity = new ProductBranchEntity();

        productBranchEntity.setBranch(branch);
        productBranchEntity.setProduct(product);
        productBranchEntity.setStock(productRequestDTO.stock());

        productBranchRepository.save(productBranchEntity);

        return productMapper.toDTO(product, productBranchEntity.getStock());
    }

    @Override
    public void deleteProduct(UUID branchId, UUID productId) {
        var productBranchEntity = productBranchRepository
                .findByBranchIdAndProductId(branchId, productId)
                .orElseThrow(() ->
                        new ConvocatoriaException("Product not found.", ExceptionTypes.NOT_FOUND)
                );
        productBranchRepository.delete(productBranchEntity);
    }

    @Override
    public ProductResponseDTO updateStock(
            UUID branchId,
            UUID productId,
            ProductStockRequestDTO productStockRequestDTO
    ) {
        var productBranchEntity = productBranchRepository.findByBranchIdAndProductId(branchId, productId)
                .orElseThrow(() ->
                        new ConvocatoriaException("Product not found.", ExceptionTypes.NOT_FOUND)
                );

        productBranchEntity.setStock(productStockRequestDTO.stock());

        var savedProductBranch = productBranchRepository.save(productBranchEntity);

        ProductEntity product = savedProductBranch.getProduct();

        return productBranchMapper.toDTO(product, savedProductBranch.getStock());
    }
}
