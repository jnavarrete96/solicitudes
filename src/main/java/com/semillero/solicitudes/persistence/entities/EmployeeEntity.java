package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="empleado")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_empleado")
    private Integer nmIdEmployee;

    @Basic(optional = false)
    @Column(name = "nm_documento")
    private Integer nmDocument;

    @Basic(optional = false)
    @Column(name = "ds_tipo_documento")
    private String dsDocumentType;

    @Basic(optional = false)
    @Column(name = "ds_nombre")
    private String dsName;

    @Basic(optional = false)
    @Column(name = "ds_apellido")
    private String dsLastName;

    @Basic(optional = false)
    @Column(name = "ds_telefono")
    private String dsTelephone;

    @Column(name = "ds_direccion")
    private String dsAddress;

    @Basic(optional = false)
    @Column(name = "fe_fecha_ingreso")
    private LocalDate feDateEntry;

    @Column(name = "fe_fecha_retiro")
    private LocalDate feDateDeparture;

    @Basic(optional = false)
    @Column(name = "ds_tipo_contrato")
    private String dsTypeOfContract;

    @Basic(optional = false)
    @Column(name = "ds_estado_empleado")
    private String dsEmployeeStatus;

    @OneToMany(mappedBy = "employeeEntity",
            targetEntity = UserEntity.class)
    private List<UserEntity> userEntity;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employeeSupervisor"
    )
    private List<EmployeeEntity> employeeEntity;

    @ManyToOne(optional = true)
    @JoinColumn(name = "nm_supervisor_inmediato")
    private EmployeeEntity employeeSupervisor;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = PositionEntity.class,
            optional = true
    )
    @JoinColumn(name = "nm_cargo")
    private PositionEntity nmPosition;
}
