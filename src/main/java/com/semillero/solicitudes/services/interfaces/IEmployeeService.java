package com.semillero.solicitudes.services.interfaces;


import com.semillero.solicitudes.persistence.dto.EmployeeDTO;

import java.util.List;

public interface IEmployeeService {

    //Listar empleados
    public List<EmployeeDTO> getEmployees();

    //Obtener empleado
    public EmployeeDTO getEmployeeById(Integer nmIdEmployee);

    //Crear empleado
    public EmployeeDTO createEmployee(EmployeeDTO employee);

    //Actualizar datos de empleado
    public EmployeeDTO updateEmployee(Integer nmIdEmployee,EmployeeDTO employee);

    //Eliminar empleado
    public Boolean deleteEmployee(Integer nmIdEmployee);
}
