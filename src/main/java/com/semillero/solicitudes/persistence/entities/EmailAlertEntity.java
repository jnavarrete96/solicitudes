package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="alerta")
public class EmailAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_alerta")
    private Integer nmIdAlert;

    @Basic(optional = false)
    @Column(name = "ds_asunto")
    private String nmSubject;

    @Basic(optional = false)
    @Column(name = "ds_destinatario")
    private String dsRecipient;

    @Column(name = "ds_contenido_alerta")
    private String dsBody;

    @Column(name = "ds_estado_alerta")
    private String dsStatus;

    @Basic(optional = false)
    @Column(name = "fe_fecha_creacion",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feCreated;
}
