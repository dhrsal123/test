package edu.ut.convocatoria.mapper;

import edu.ut.convocatoria.domain.dto.response.ProductStockResponseDTO;
import edu.ut.convocatoria.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductBranchMapper {

    @Mapping(target = "name", source = "productEntity.name")
    @Mapping(target = "description", source = "productEntity.description")
    @Mapping(target = "price", source = "productEntity.price")
    @Mapping(target = "stock", source = "stock")
    ProductStockResponseDTO toDTO(ProductEntity productEntity, Integer stock);
}
