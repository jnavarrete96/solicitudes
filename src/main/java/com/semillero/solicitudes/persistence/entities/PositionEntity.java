package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name="cargos")

public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_cargo")
    private Integer nmIdPosition;

    @Basic(optional = false)
    @Column(name = "ds_cargo")
    private String dsPosition;

    @Column(name = "ds_descripcion")
    private String dsDescription;

    @Basic(optional = false)
    @Column(name = "fe_fecha_creacion" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feCreated;

    @Basic(optional = false)
    @Column(name = "ds_activo")
    private String dsStatus;

    @OneToMany(mappedBy = "nmPosition",
            targetEntity = EmployeeEntity.class)

    private List<EmployeeEntity> employeeEntity;
}
