package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO employeeToEmployeeDTO(EmployeeEntity employeeEntity);

    @InheritInverseConfiguration
    EmployeeEntity employeeDtoToEmployee(EmployeeDTO employeeDTO);
}
