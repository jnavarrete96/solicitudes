package com.semillero.solicitudes.persistence.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class RequestVacationDTO {

    private Integer nmIdRequest;

    private Integer nmNumberDaysRequested;

    @NotNull(message = "Fecha de inicio requerida")
    private LocalDate feStartDate;

    private LocalDate feEndDate;

    private LocalDate feReinstatementDate;

    @NotEmpty(message = "Estado requerido")
    private String dsStatus;

    private String dsObservation;
    private LocalDateTime feCreated;
}
