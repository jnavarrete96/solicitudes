package com.semillero.solicitudes.services.interfaces;


import com.semillero.solicitudes.persistence.dto.EmployeeDTO;

import java.util.List;

public interface IEmployeeService {
    public List<EmployeeDTO> getEmployees();
}
