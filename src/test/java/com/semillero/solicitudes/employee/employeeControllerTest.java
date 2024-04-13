package com.semillero.solicitudes.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.*;
import java.time.LocalDate;

@SpringBootTest
@WebAppConfiguration
public class employeeControllerTest {
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //Test de Mostrar empleados controlador
    @DisplayName("Show All Employees")
    @Test
    public void testShowAllEmployees() throws Exception{
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200,res.getResponse().getStatus());
    }

    //Test de crear empleados controlador
    @DisplayName("Create Employee")
    @Test
    @Transactional
    public void testCreateEmployee() throws Exception{
        EmployeeDTO employeeDTO = createEmployeeDTO(30,1185843218
                                                    ,"CC","Enzo"
                                                    ,"Gonzales","3333565646"
                                                    ,"Calle 25o",LocalDate.parse("2023-06-18")
                                                    ,"Indefinido","Activo");
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(mapToJson(employeeDTO)))
                .andReturn();
        assertEquals(201,res.getResponse().getStatus());
    }

    //Convertir obj a Json
    private String mapToJson (Object obj) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        return mapper.writeValueAsString(obj);
    }

    //Crear el DTO empleado
    private EmployeeDTO createEmployeeDTO(Integer nmIdEmployee, Integer nmDocument,
                                          String dsDocumentType, String dsName,
                                          String dsLastName, String dsTelephone,
                                          String dsAddress, LocalDate feDateEntry,
                                          String dsTypeOfContract, String dsEmployeeStatus){
        return EmployeeDTO.builder()
                .nmIdEmployee(nmIdEmployee)
                .nmDocument(nmDocument)
                .dsDocumentType(dsDocumentType)
                .dsName(dsName)
                .dsLastName(dsLastName)
                .dsTelephone(dsTelephone)
                .dsAddress(dsAddress)
                .feDateEntry(feDateEntry)
                .feDateDeparture(null)
                .dsTypeOfContract(dsTypeOfContract)
                .dsEmployeeStatus(dsEmployeeStatus)
                .build();

    }
}
