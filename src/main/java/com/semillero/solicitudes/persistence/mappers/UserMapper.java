package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.UserDTO;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDTO userToUserDTO (UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity userDtoToUserEntity (UserDTO userDTO);
}
