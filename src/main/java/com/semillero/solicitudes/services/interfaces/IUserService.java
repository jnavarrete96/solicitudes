package com.semillero.solicitudes.services.interfaces;

import com.semillero.solicitudes.persistence.dto.UserDTO;

import java.util.List;

public interface IUserService {
    //Listar usuarios
    public List<UserDTO> getAllUsers();

    //Crear Usuario
    public UserDTO createUser(UserDTO user,Integer nmIdEmployee);
}
