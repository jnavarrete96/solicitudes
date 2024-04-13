package com.semillero.solicitudes.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.*;

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
}
