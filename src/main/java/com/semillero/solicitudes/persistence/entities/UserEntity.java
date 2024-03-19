package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name="usuario")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_usuario")
    private Integer nmIdUser;

    @Basic(optional = false)
    @Column(name = "ds_usuario")
    private String dsUser;

    @Column(name = "fe_fecha_creacion")
    private LocalDate feCreated;

    @Basic(optional = false)
    @Column(name = "ds_activo")
    private String dsStatus;

    @Basic(optional = false)
    @Column(name = "ds_contraseña")
    private String dsPassword;

    @Column(name = "email")
    private String dsEmail;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = UserRolEntity.class)
    @JoinColumn(name = "nm_rol")
    private UserRolEntity rolUser;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = EmployeeEntity.class)
    @JoinColumn(name = "nm_id_empleado")
    private EmployeeEntity employeeEntity;

    @OneToMany(
            mappedBy = "userEntity",
            targetEntity = RequestVacationEntity.class)
    private List<RequestVacationEntity> requestVacationEntity;
}
