package edu.ut.convocatoria.mapper;

import edu.ut.convocatoria.domain.dto.request.UserRequestDTO;
import edu.ut.convocatoria.domain.dto.response.UserResponseDTO;
import edu.ut.convocatoria.domain.entity.RoleEntity;
import edu.ut.convocatoria.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", source = "roles")
    UserEntity toEntity(UserRequestDTO user,
                        String username,
                        String password,
                        Set<RoleEntity> roles);

    @Mapping(target = "token", source = "token")
    UserResponseDTO toDTO(UserEntity user, String token);
}
