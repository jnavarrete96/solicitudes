package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name="solicitud_vaciones")

public class RequestVacationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_solicitud")
    private Integer nmIdRequest;

    @Basic(optional = false)
    @Column(name = "nm_dias_solicita")
    private Integer nmNumberDaysRequested;

    @Basic(optional = false)
    @Column(name = "fe_fecha_inicio")
    private LocalDate feStartDate;

    @Basic(optional = false)
    @Column(name = "fe_fecha_fin")
    private LocalDate feEndDate;

    @Basic(optional = false)
    @Column(name = "fe_fecha_retorna")
    private LocalDate feReinstatementDate;

    @Basic(optional = false)
    @Column(name = "ds_estado")
    private String dsStatus;

    @Column(name = "ds_observaciones")
    private String dsObservation;

    @Basic(optional = false)
    @Column(name = "fe_fecha_creacion")
    private LocalDate feCreated;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = UserEntity.class
    )
    @JoinColumn(name = "nm_id_usuario")
    private UserEntity userEntity;
}
