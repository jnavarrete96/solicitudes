package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.exception.BadRequestException;
import com.semillero.solicitudes.exception.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.payload.MessageResponse;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;


    //listar empleados
    @GetMapping("employees")
    public ResponseEntity<?> showAllEmployees(){
        List<EmployeeDTO> employees = employeeService.getEmployees();
        if (employees.isEmpty() || employees==null){
            throw new ResourceNotFoundException("empleados");
        }else{
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(employees)
                    .build()
                    ,HttpStatus.OK);
        }
    }

    //Obtener empleado
    @GetMapping("employee/{nmIdEmployee}")
    public ResponseEntity<?> showById(@PathVariable Integer nmIdEmployee){
        EmployeeDTO employee = employeeService.getEmployeeById(nmIdEmployee);
        if(employee==null){
            throw new ResourceNotFoundException("empleado", "nmIdEmployee", nmIdEmployee);
        }else{
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(employee)
                    .build()
                    ,HttpStatus.OK);
        }
    }

    //Crear empleado
    @PostMapping("employee")
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeDTO employee){
        try{
            EmployeeDTO employeeDTO = employeeService.createEmployee(employee);
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(employeeDTO)
                    .build()
                    ,HttpStatus.CREATED);

        }catch(DataAccessException exDt){
            throw new BadRequestException(exDt.getMessage());
        }
    }

    //Actualizar empleado
    @PutMapping("employee/{nmIdEmployee}")
    public ResponseEntity<?> update(@RequestBody @Valid EmployeeDTO employee,@PathVariable Integer nmIdEmployee){
            EmployeeDTO employeeDTO = employeeService.updateEmployee(nmIdEmployee,employee);
            if (employeeDTO!=null){
                try{
                    return new ResponseEntity<>(MessageResponse
                            .builder()
                            .message("")
                            .object(employeeDTO)
                            .build()
                            ,HttpStatus.CREATED);
                }catch(DataAccessException exDt){
                    throw  new BadRequestException(exDt.getMessage());
                }
            }else{
                throw new ResourceNotFoundException("empleado", "nmIdEmployee", nmIdEmployee);
            }
    }

    //Eliminar empleado
    @DeleteMapping("employee/{nmIdEmployee}")
    public ResponseEntity<?> delete(@PathVariable Integer nmIdEmployee){
        Boolean employee= this.employeeService.deleteEmployee(nmIdEmployee);
        if(employee!=null){
            return new ResponseEntity<>(employee,HttpStatus.NO_CONTENT);
        }else{
            throw new ResourceNotFoundException("empleado", "nmIdEmployee", nmIdEmployee);
        }
    }

}
