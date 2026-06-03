package edu.ut.convocatoria.mapper;

import edu.ut.convocatoria.domain.dto.response.MaxStockResponseDTO;
import edu.ut.convocatoria.domain.dto.response.ProductStockResponseDTO;
import edu.ut.convocatoria.domain.entity.MaxStockProductProjection;
import edu.ut.convocatoria.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProductBranchMapper {

    @Mapping(target = "name", source = "productEntity.name")
    @Mapping(target = "description", source = "productEntity.description")
    @Mapping(target = "price", source = "productEntity.price")
    @Mapping(target = "stock", source = "stock")
    ProductStockResponseDTO toDTO(ProductEntity productEntity, Integer stock);

    @Mapping(target = "branchId", source = "branchId")
    @Mapping(target = "maxStockProduct.id", source = "productId")
    @Mapping(target = "maxStockProduct.name", source = "name")
    @Mapping(target = "maxStockProduct.description", source = "description")
    @Mapping(target = "maxStockProduct.price", source = "price")
    MaxStockResponseDTO toMaxStockDto(MaxStockProductProjection projection);
}
