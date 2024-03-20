package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exception.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dao.EmployeeDao;
import com.semillero.solicitudes.persistence.dao.UserDao;
import com.semillero.solicitudes.persistence.dto.UserDTO;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.mappers.UserMapper;
import com.semillero.solicitudes.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserImplService implements IUserService {

    @Autowired
    private UserDao userDao;
    private UserMapper userMapper;
    private EmployeeDao employeeDao;

    //Listar Usuarios

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userDao.findAll();
        List<UserDTO> userDTOS = users.stream().map(
                user -> userMapper.userToUserDTO(user)).collect(Collectors.toList());
        return userDTOS;
    }

    //Crear Usuario
    @Override
    public UserDTO createUser(UserDTO user, Integer nmIdEmployee) {
        // Verificar si existe un empleado con el ID proporcionado
        Optional<EmployeeEntity> optionalEmployee = employeeDao.findById(nmIdEmployee.longValue());
        if (optionalEmployee.isEmpty()) {
            throw new ResourceNotFoundException("Empleado", "nmIdEmployee ", nmIdEmployee);
        }
        // Obtener la fecha actual
        LocalDate defaultCreationDate = LocalDate.now();

        // Asignar la fecha predeterminada si la fecha de creación es nula en el DTO de usuario
        if (user.getFeCreated() == null) {
            user.setFeCreated(defaultCreationDate);
        }
        // Mapear el DTO de usuario a una entidad de usuario
        UserEntity userEntity = userMapper.userDtoToUserEntity(user);

        // Asociar el empleado con nuevo usuario
        EmployeeEntity employeeEntity = optionalEmployee.get();
        userEntity.setEmployeeEntity(employeeEntity);

        // Guardar el nuevo usuario en la base de datos
        UserEntity savedUserEntity = userDao.save(userEntity);

        // Mapear la entidad de usuario guardada a un DTO de usuario y devolverlo
        return userMapper.userToUserDTO(savedUserEntity);
    }
}
