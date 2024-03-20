package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.exception.BadRequestException;
import com.semillero.solicitudes.exception.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.UserDTO;
import com.semillero.solicitudes.persistence.payload.MessageResponse;
import com.semillero.solicitudes.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUserService userService;

    //Listar Usuarios
    @GetMapping("/users")
    public ResponseEntity<?> showAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty() || users==null){
            throw new ResourceNotFoundException("usuarios");
        }else{
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(users)
                    .build()
                    ,HttpStatus.OK);
        }
    }

    //Crear Usuario
    @PostMapping("user")
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO user,@RequestParam Integer nmIdEmployee){
        try{
            UserDTO userDTO = userService.createUser(user,nmIdEmployee);
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(userDTO)
                    .build()
                    ,HttpStatus.CREATED);
        }catch (DataAccessException exDt){
            throw new BadRequestException(exDt.getMessage());
        }
    }
}
