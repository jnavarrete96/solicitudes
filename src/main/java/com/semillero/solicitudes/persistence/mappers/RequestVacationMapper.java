package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.RequestVacationDTO;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RequestVacationMapper {
    RequestVacationDTO requestVacationToRequestVacationDTO(RequestVacationEntity requestVacationEntity);

    @InheritInverseConfiguration
    RequestVacationEntity requestVacationDtoToRequestVacation(RequestVacationDTO requestVacationDTO);
}
