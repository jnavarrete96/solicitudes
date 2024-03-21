package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exception.BadRequestException;
import com.semillero.solicitudes.exception.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dao.RequestVacationDao;
import com.semillero.solicitudes.persistence.dao.UserDao;
import com.semillero.solicitudes.persistence.dto.RequestVacationDTO;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.mappers.RequestVacationMapper;
import com.semillero.solicitudes.services.interfaces.IRequestVacationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestVacationImplService implements IRequestVacationService {

    private RequestVacationDao requestVacationDao;
    private RequestVacationMapper requestVacationMapper;
    private UserDao userDao;

    @Override
    public List<RequestVacationDTO> getAllRequestVacation(Integer nmIdUser){
        // Verificar si existe un usuario
        verifyUser(nmIdUser);
        //Encontrar las solicitudes por el usuario
        List<RequestVacationEntity> requestVacationEntities =
                this.requestVacationDao.findRequestVacationByUser(nmIdUser);
        //Validar si tiene solicitudes
        if(requestVacationEntities.isEmpty()){
            throw new ResourceNotFoundException("solicitudes");
        }
        //Mappear para mostrar en el sistema
        List<RequestVacationDTO> requestVacationDTOS = requestVacationEntities.stream().map(
                req -> this.requestVacationMapper.requestVacationToRequestVacationDTO(req)
            ).collect(Collectors.toList());
        return requestVacationDTOS;
    }

    @Override
    public List<RequestVacationDTO> getRequestVacationByIdAndUser(Integer nmIdRequest, Integer nmIdUser) {
        // Verificar si existe un usuario
        verifyUser(nmIdUser);
        //Encontrar las solicitud por el usuario
        List<RequestVacationEntity> requestVacationEntitie =
                this.requestVacationDao.findRequestVacationByIdAndUser(nmIdRequest,nmIdUser);
        //Validar si tiene solicitud
        if(requestVacationEntitie.isEmpty()){
            throw new ResourceNotFoundException("solicitud");
        }
        //Mappear para mostrar en el sistema
        List<RequestVacationDTO> requestVacationDTO = requestVacationEntitie.stream().map(
                req -> this.requestVacationMapper.requestVacationToRequestVacationDTO(req)
        ).collect(Collectors.toList());
        return requestVacationDTO;
    }

    @Override
    public RequestVacationDTO createRequestVacation(RequestVacationDTO requestVacation, Integer nmIdUser) {
        // Validaciones iniciales
        verifyUser(nmIdUser);
        UserEntity user = this.userDao.findById(nmIdUser).get();
        verifyMonth(user);
        boolean oneYearCompleted = verifyOneYear(user);
        String typeOfContract = user.getEmployeeEntity().getDsTypeOfContract();
        if ("Prestación de servicios".equals(typeOfContract)) {
            throw new BadRequestException("No se puede solicitar vacaciones con un contrato por prestación de servicios");
        }
        //No ha cumplido el año
        if (!oneYearCompleted){
            int accumulatedDays=calculateAccumulatedDays(user); //Dias acumulados
            requestVacation.setNmNumberDaysRequested(accumulatedDays);
            //Obtenemos el dia inicio de vacaciones
            LocalDate feStartDate = requestVacation.getFeStartDate();
            requestVacation.setFeStartDate(feStartDate);
            //Calculamos la fecha final de vacaciones
            LocalDate feEndDate = calculateEndDate(feStartDate,accumulatedDays);
            requestVacation.setFeEndDate(feEndDate);
            //Calcular la fecha de regreso
            LocalDate feReturnDate = calculateReturnDate(feEndDate);
            requestVacation.setFeReinstatementDate(feReturnDate);
        }
        //Ya cumplió el año
        else if(oneYearCompleted){
            if(requestVacation.getNmNumberDaysRequested()==null){
                throw new BadRequestException("número de días requerido");
            }
            if(requestVacation.getNmNumberDaysRequested()<=6 || requestVacation.getNmNumberDaysRequested()>15){
                throw new BadRequestException("número de días fuera de rango.(min 6 y max 15)");
            }
            //Calculamos la fecha final de vacaciones
            LocalDate feEndDate = calculateEndDate(requestVacation.getFeStartDate()
                                                   ,requestVacation.getNmNumberDaysRequested());
            requestVacation.setFeEndDate(feEndDate);
            //Calcular la fecha de regreso
            LocalDate feReturnDate = calculateReturnDate(feEndDate);
            requestVacation.setFeReinstatementDate(feReturnDate);
        }

        // Asignar fecha de creacion cuando se realiza
        if (requestVacation.getFeCreated() == null) {
            requestVacation.setFeCreated(LocalDateTime.now());
        }
        validateVacationRequestDate(requestVacation.getFeStartDate());
        RequestVacationEntity requestVacationEntity = createRequest(requestVacation,user);
        RequestVacationEntity requestSaved = this.requestVacationDao.save(requestVacationEntity);
        return this.requestVacationMapper.requestVacationToRequestVacationDTO(requestSaved);
    }

    //Verificar si el usuario existe
    private void verifyUser (Integer nmIdUser){
        Optional<UserEntity> optionalUser = userDao.findById(nmIdUser);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", "nmIdUser", nmIdUser);
        }
    }

    //Verificar los dos meses de prueba
    private void verifyMonth (UserEntity user){
        LocalDate hireDate = user.getEmployeeEntity().getFeDateEntry();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(hireDate, currentDate);
        int months = period.getMonths();
        int years = period.getYears();
        if(months <= 2 && years==0) {
            throw new BadRequestException("periodo mínimo de dos meses");
        }
    }

    //Verificar si cumplió el año
    private boolean verifyOneYear (UserEntity user){
        LocalDate hireDate = user.getEmployeeEntity().getFeDateEntry();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(hireDate, currentDate);
        int year = period.getYears();
        return year >= 1;
    }

    //Calcular el numero de días acumulados
    private int calculateAccumulatedDays(UserEntity user){
        LocalDate hireDate = user.getEmployeeEntity().getFeDateEntry();
        LocalDate current = LocalDate.now();
        Long workedDays = ChronoUnit.DAYS.between(hireDate,current);

        int vacationDaysPerYear = 15;
        int accumulatedDays = Math.round(workedDays*vacationDaysPerYear/365f);
        return accumulatedDays;
    }

    //Validar dias no hábiles
    private boolean isBusinessDay(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    //Calcular el ultimo dia de vacaciones
    private LocalDate calculateEndDate(LocalDate startDate, int numberOfDays) {
        LocalDate endDate = startDate;
        for (int i = 0; i < numberOfDays-1; i++) {
            endDate = endDate.plusDays(1); // Añadir un día a la fecha de inicio por cada día de vacaciones
            while (!isBusinessDay(endDate)) {
                endDate = endDate.plusDays(1); // Si la fecha no es hábil, avanzar al siguiente día hábil
            }
        }
        return endDate;
    }

    //Calcular el dia de regreso
    private LocalDate calculateReturnDate(LocalDate endDate) {
        LocalDate returnDate = endDate.plusDays(1); // Añadir un día a la fecha de finalización de las vacaciones
        while (!isBusinessDay(returnDate)) {
            returnDate = returnDate.plusDays(1); // Si la fecha no es hábil, avanzar al siguiente día hábil
        }
        return returnDate;
    }

    //Validar que la fecha solicitada son 15 días de anticipación
    private void validateVacationRequestDate(LocalDate feStartDate){
        LocalDate currentDate = LocalDate.now();
        Long anticipationDays = ChronoUnit.DAYS.between(currentDate,feStartDate);
        if(anticipationDays < 15){
            throw new BadRequestException("Solicitar con 15 días de anticipación");
        }
    }

    //obtener los datos
    private RequestVacationEntity createRequest (RequestVacationDTO requestVacation,UserEntity user){
        RequestVacationEntity requestVacationEntity = new RequestVacationEntity();
        requestVacationEntity.setNmNumberDaysRequested(requestVacation.getNmNumberDaysRequested());
        requestVacationEntity.setFeStartDate(requestVacation.getFeStartDate());
        requestVacationEntity.setFeEndDate(requestVacation.getFeEndDate());
        requestVacationEntity.setFeReinstatementDate(requestVacation.getFeReinstatementDate());
        requestVacationEntity.setDsStatus(requestVacation.getDsStatus());
        requestVacationEntity.setDsObservation(requestVacation.getDsObservation());
        requestVacationEntity.setFeCreated(requestVacation.getFeCreated());
        requestVacationEntity.setUserEntity(user);
        return requestVacationEntity;
    }

}
