package com.semillero.solicitudes.services.interfaces;

import com.semillero.solicitudes.persistence.dto.RequestVacationDTO;

import java.util.List;

public interface IRequestVacationService {

    //Traer todas las solicitudes de un usuario
    public List<RequestVacationDTO> getAllRequestVacation(Integer nmIdUser);

    //Traer una solicitud en especifico de un usuario
    public List<RequestVacationDTO> getRequestVacationByIdAndUser(Integer nmIdRequest,Integer nmIdUser);

    //Crear una solicitud
    public RequestVacationDTO createRequestVacation(RequestVacationDTO requestVacation,Integer nmIdUser);
}
