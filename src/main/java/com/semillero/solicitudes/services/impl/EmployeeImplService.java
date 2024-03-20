package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.persistence.dao.EmployeeDao;
import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.mappers.EmployeeMapper;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeImplService implements IEmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> getEmployees(){
        List<EmployeeEntity> employees=employeeDao.findAll();
        List<EmployeeDTO> employeeDTOS=employees.stream().map(
                employee -> employeeMapper.employeeToEmployeeDTO(employee)).collect(Collectors.toList());

        return employeeDTOS;
    }

    //Obtener empleado
    @Override
    public EmployeeDTO getEmployeeById(Integer nmIdEmployee){
        //Verificar si existe el empleado
        Optional<EmployeeEntity> optionalEmployee = this.employeeDao.findById(nmIdEmployee.longValue());
        if (optionalEmployee.isPresent()){
            EmployeeEntity employee = optionalEmployee.get();
            return this.employeeMapper.employeeToEmployeeDTO(employee);
        }else{
            return null;
        }
    }

    //Crear empleado
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employee){
        // Convertir el DTO a una entidad de empleado
        EmployeeEntity employeeEntity = employeeMapper.employeeDtoToEmployee(employee);
        // Guardar la entidad del empleado en la base de datos
        EmployeeEntity savedEmployee = employeeDao.save(employeeEntity);
        // Convertir la entidad guardada a un DTO y devolverla como respuesta
        return employeeMapper.employeeToEmployeeDTO(savedEmployee);
    }

    //Actualizar datos de empleado
    @Transactional
    @Override
    public EmployeeDTO updateEmployee(Integer nmIdEmployee, EmployeeDTO employee){
        //Verificar si existe el empleado
        Optional<EmployeeEntity> optionalEmployee = this.employeeDao.findById(nmIdEmployee.longValue());
        if (optionalEmployee.isPresent()){
            // Obtener la entidad existente del empleado
            EmployeeEntity existingEmployee = optionalEmployee.get();

            // Copiar las propiedades del DTO a la entidad
            BeanUtils.copyProperties(employee, existingEmployee,"nmIdEmployee");

            // Guardar la entidad actualizada en la base de datos
            EmployeeEntity updatedEmployeeEntity = this.employeeDao.save(existingEmployee);

            // Devolver el DTO actualizado
            return employeeMapper.employeeToEmployeeDTO(updatedEmployeeEntity) ;
        }else{
            return null;
        }
    }

    //Eliminar empleado
    @Override
    public Boolean deleteEmployee(Integer nmIdEmployee) {
        //Verificar si existe el empleado
        Optional<EmployeeEntity> optionalEmployee = this.employeeDao.findById(nmIdEmployee.longValue());
        if (optionalEmployee.isPresent()) {
            // Obtener la entidad existente del empleado
            EmployeeEntity existingEmployee = optionalEmployee.get();
            if (existingEmployee != null) {
                this.employeeDao.delete(existingEmployee);
                return true;
            } else {
                return false;
            }
        }else {
            return null;
        }
    }
}
