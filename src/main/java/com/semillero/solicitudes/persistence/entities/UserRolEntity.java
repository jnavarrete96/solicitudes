package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name="rol_usuario")

public class UserRolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_rol")
    private Integer nmIdRol;

    @Basic(optional = false)
    @Column(name = "ds_rol")
    private String dsRol;

    @Column(name = "fe_fecha_creacion")
    private LocalDate feCreated;

    @OneToMany(mappedBy = "rolUser",
            targetEntity = UserEntity.class)
    private List<UserEntity> userEntity;
}
