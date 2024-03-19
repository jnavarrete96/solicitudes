package com.semillero.solicitudes.persistence.dto;

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
    private Integer nmDocument;
    private String dsDocumentType;
    private String dsName;
    private String dsLastName;
    private String dsTelephone;
    private String dsAddress;
    private LocalDate feDateEntry;
    private LocalDate feDateDeparture;
    private String dsTypeOfContract;
    private String dsEmployeeStatus;
}
