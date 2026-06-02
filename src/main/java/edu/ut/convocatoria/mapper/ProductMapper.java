package edu.ut.convocatoria.mapper;

import edu.ut.convocatoria.domain.dto.request.ProductRequestDTO;
import edu.ut.convocatoria.domain.dto.response.ProductResponseDTO;
import edu.ut.convocatoria.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductEntity toEntity(ProductRequestDTO productRequestDTO);

    ProductResponseDTO toDTO(ProductEntity productEntity);
}
