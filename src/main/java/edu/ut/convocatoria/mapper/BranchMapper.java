package edu.ut.convocatoria.mapper;

import edu.ut.convocatoria.domain.dto.request.BranchRequestDTO;
import edu.ut.convocatoria.domain.dto.response.BranchResponseDTO;
import edu.ut.convocatoria.domain.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchMapper {

    BranchEntity toEntity(BranchRequestDTO branchRequestDTO);

    BranchResponseDTO toDto(BranchEntity entity);

}
