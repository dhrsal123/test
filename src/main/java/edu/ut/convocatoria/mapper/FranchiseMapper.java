package edu.ut.convocatoria.mapper;

import edu.ut.convocatoria.domain.dto.request.FranchiseRequestDTO;
import edu.ut.convocatoria.domain.dto.response.FranchiseResponseDTO;
import edu.ut.convocatoria.domain.entity.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface FranchiseMapper {

    FranchiseEntity toEntity(FranchiseRequestDTO franchiseRequestDTO);

    FranchiseResponseDTO toDto(FranchiseEntity entity);

}
