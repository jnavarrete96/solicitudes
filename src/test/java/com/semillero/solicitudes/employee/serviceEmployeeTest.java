package com.semillero.solicitudes.employee;

import com.semillero.solicitudes.persistence.dao.EmployeeDao;
import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.mappers.EmployeeMapper;
import com.semillero.solicitudes.services.impl.EmployeeImplService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class serviceEmployeeTest {

    @Mock
    private EmployeeDao employeeDao;
    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeImplService employeeImplService;

    private EmployeeEntity employeeEntityOne;
    private EmployeeDTO employeeDTOOne;
    private EmployeeEntity employeeEntityTwo;
    private EmployeeDTO employeeDTOTwo;

    //Inicializar
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        employeeImplService = new EmployeeImplService(employeeDao,employeeMapper);

        employeeEntityOne = createEmployeeEntity(30,1185843218
                                                ,"CC","Enzo"
                                                ,"Gonzales","3333565646"
                                                ,"Calle 25o",LocalDate.parse("2023-06-18")
                                                ,"Indefinido","Activo");

        employeeDTOOne = createEmployeeDTO(30,1185843218
                                            ,"CC","Enzo"
                                            ,"Gonzales","3333565646"
                                            ,"Calle 25o",LocalDate.parse("2023-06-18")
                                            ,"Indefinido","Activo");

        employeeEntityTwo = createEmployeeEntity(31,1110043218
                                                ,"CC","Pedro León"
                                                ,"Jaramillo","3005869446"
                                                ,"Calle 16b bis",LocalDate.parse("2023-09-25")
                                                ,"Indefinido","Activo");

        employeeDTOTwo = createEmployeeDTO(31,1110043218
                                            ,"CC","Pedro León"
                                            ,"Jaramillo","3005869446"
                                            ,"Calle 16b bis",LocalDate.parse("2023-09-25")
                                            ,"Indefinido","Activo");
    }

    //Test de listar empleados
    @DisplayName("List All Employees")
    @Test
    public void testGetEmployees(){
        List<EmployeeEntity> employeeEntities = Arrays.asList(employeeEntityOne,employeeEntityTwo);
        when(employeeDao.findAll()).thenReturn(employeeEntities);
        when(employeeMapper.employeeToEmployeeDTO(Mockito.any())).thenReturn(employeeDTOOne,employeeDTOTwo);

        List<EmployeeDTO> employeeDTOList = employeeImplService.getEmployees();
        employeeDTOList.stream().forEach(employees -> System.out.println(employees));
        assertThat(employeeDTOList).isNotNull();
    }

    //Test de crear empleado
    @DisplayName("Create Employee")
    @Test
    public void testCreateEmployee(){
        when(employeeDao.save(Mockito.any(EmployeeEntity.class))).thenReturn(employeeEntityOne);
        when(employeeMapper.employeeToEmployeeDTO(Mockito.any())).thenReturn(employeeDTOOne);

        EmployeeDTO savedEmployee = employeeImplService.createEmployee(employeeDTOOne);
        assertThat(savedEmployee).isNotNull();
    }

    //Crear la entidad empleado
    private EmployeeEntity createEmployeeEntity(Integer nmIdEmployee, Integer nmDocument,
                                                String dsDocumentType, String dsName,
                                                String dsLastName, String dsTelephone,
                                                String dsAddress, LocalDate feDateEntry,
                                                String dsTypeOfContract, String dsEmployeeStatus){
        return EmployeeEntity.builder()
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
