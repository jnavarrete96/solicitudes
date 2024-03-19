package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.payload.MessageResponse;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (employees.isEmpty()){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(employees)
                    .build()
                    ,HttpStatus.OK);
        }
    }
}
