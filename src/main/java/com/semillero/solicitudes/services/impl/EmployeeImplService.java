package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.persistence.dao.EmployeeDao;
import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.mappers.EmployeeMapper;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeImplService implements IEmployeeService {


    private EmployeeDao employeeDao;
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> getEmployees(){
        List<EmployeeEntity> employees=employeeDao.findAll();
        List<EmployeeDTO> employeeDTOS=employees.stream().map(
                employee -> employeeMapper.employeeToEmployeeDTO(employee)).collect(Collectors.toList());

        return employeeDTOS;
    }

}
