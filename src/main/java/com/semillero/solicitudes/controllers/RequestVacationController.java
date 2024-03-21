package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.exception.BadRequestException;
import com.semillero.solicitudes.persistence.dto.RequestVacationDTO;
import com.semillero.solicitudes.persistence.payload.MessageResponse;
import com.semillero.solicitudes.services.interfaces.IRequestVacationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RequestVacationController {

    @Autowired
    private IRequestVacationService requestVacationService;

    //Mostrar todas las solicitudes por usuario
    @GetMapping(value = "requestVacation/{nmIdUser}")
    public ResponseEntity<?> getAllRequestVacationByUser(@PathVariable Integer nmIdUser){
        List<RequestVacationDTO> requestVacation = requestVacationService.getAllRequestVacation(nmIdUser);
        return new ResponseEntity<>(MessageResponse
                .builder()
                .message("")
                .object(requestVacation)
                .build()
                ,HttpStatus.OK);
    }
    //Mostrar las solicitud de unusuario
    @GetMapping(value = "requestVacation/{nmIdRequest}/{nmIdUser}")
    public ResponseEntity<?> getRequestVacationByIdAndUser(
            @PathVariable Integer nmIdRequest,
            @PathVariable Integer nmIdUser){
        List<RequestVacationDTO> requestVacation = requestVacationService
                                            .getRequestVacationByIdAndUser(nmIdRequest,nmIdUser);
        return new ResponseEntity<>(MessageResponse
                .builder()
                .message("")
                .object(requestVacation)
                .build()
                ,HttpStatus.OK);
    }

    //Crear la solicitud
    @PostMapping(value = "create/{nmIdUser}")
    public ResponseEntity<?> createRequest(@RequestBody @Valid RequestVacationDTO requestVacation
                                            ,@PathVariable Integer nmIdUser){
        try{
            RequestVacationDTO requestVacationDTO =
                    this.requestVacationService.createRequestVacation(requestVacation,nmIdUser);
            return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("")
                    .object(requestVacationDTO)
                    .build()
                    ,HttpStatus.CREATED);
        }catch (DataAccessException exDt){
            throw new BadRequestException(exDt.getMessage());
        }
    }
}
