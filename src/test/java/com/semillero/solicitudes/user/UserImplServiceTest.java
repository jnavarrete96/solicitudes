package com.semillero.solicitudes.user;

import com.semillero.solicitudes.persistence.dao.EmployeeDao;
import com.semillero.solicitudes.persistence.dao.UserDao;
import com.semillero.solicitudes.persistence.dto.EmployeeDTO;
import com.semillero.solicitudes.persistence.dto.UserDTO;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.mappers.UserMapper;
import com.semillero.solicitudes.services.impl.UserImplService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserImplServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private EmployeeDao employeeDao;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserImplService userImplService;

    private UserEntity userEntityOne;
    private UserEntity userEntityTwo;
    private UserDTO userDTOOne;
    private UserDTO userDTOTwo;

    private EmployeeEntity employeeEntityOne;
    private EmployeeEntity employeeEntityTwo;
    private EmployeeDTO employeeDTOOne;
    private EmployeeDTO employeeDTOTwo;

    //Inicializar
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);

        //Employees
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

        //Users
        userEntityOne = createUserEntity(20,"enzo20","enzo12345",
                "enzo20@gmail.com","Activo",new EmployeeEntity());

        userDTOOne = createUserDTO(20,"enzo20","enzo12345",
                "enzo20@gmail.com","Activo");

        userEntityTwo = createUserEntity(21,"pedro21","pedroL12345",
                "pedroL21@gmail.com","Activo",new EmployeeEntity());

        userDTOTwo = createUserDTO(21,"pedro21","pedroL12345",
                "pedroL21@gmail.com","Activo");

    }

    //Test listar usuarios
    @DisplayName("Get all Users")
    @Test
    public void testShowAllUser(){
        List<UserEntity> userEntities = Arrays.asList(userEntityOne,userEntityTwo);
        when(userDao.findAll()).thenReturn(userEntities);
        when(userMapper.userToUserDTO(Mockito.any())).thenReturn(userDTOOne,userDTOTwo);

        List<UserDTO> userDTOS = userImplService.getAllUsers();
        userDTOS.stream().forEach(users -> System.out.println(users));
        assertThat(userDTOS).isNotNull();
    }

    //Test crear usuario
    @DisplayName("Create User")
    @Test
    public void testCreateUser(){
        Mockito.when(employeeDao.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(employeeEntityOne));
        when(userDao.save(Mockito.any(UserEntity.class))).thenReturn(userEntityOne);
        when(userMapper.userToUserDTO(Mockito.any())).thenReturn(userDTOOne);
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

    //Crear la entidad usuario
    private UserEntity createUserEntity(Integer nmIdUser,String dsUser,String dsPassword,String dsEmail,
                                        String dsStatus,EmployeeEntity employee){
        return UserEntity.builder()
                .nmIdUser(nmIdUser)
                .dsUser(dsUser)
                .dsPassword(dsPassword)
                .dsEmail(dsEmail)
                .dsStatus(dsStatus)
                .employeeEntity(employee)
                .build();
    }

    //Crear usuario DTO
    private UserDTO createUserDTO(Integer nmIdUser,String dsUser,String dsPassword,String dsEmail,
                                        String dsStatus){
        return UserDTO.builder()
                .nmIdUser(nmIdUser)
                .dsUser(dsUser)
                .dsPassword(dsPassword)
                .dsEmail(dsEmail)
                .dsStatus(dsStatus)
                .build();
    }
}
