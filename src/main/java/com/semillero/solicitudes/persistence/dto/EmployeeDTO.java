package com.semillero.solicitudes.persistence.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class EmployeeDTO {

    private Integer nmIdEmployee;

    @NotNull(message = "Numero de documento requerido")
    private Integer nmDocument;

    @NotEmpty(message = "Tipo de documento requerido")
    private String dsDocumentType;

    @NotEmpty(message = "Nombre requerido")
    private String dsName;

    @NotEmpty(message = "Apellidos requeridos")
    private String dsLastName;

    @NotEmpty(message = "Telefono requerido")
    private String dsTelephone;

    private String dsAddress;

    @NotNull(message = "Fecha requerida")
    private LocalDate feDateEntry;

    private LocalDate feDateDeparture;

    @NotEmpty(message = "Tipo de contrato requerido")
    private String dsTypeOfContract;

    @NotEmpty(message = "Estado del empleado requerido")
    private String dsEmployeeStatus;
}
