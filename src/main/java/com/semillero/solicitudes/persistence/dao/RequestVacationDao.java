package com.semillero.solicitudes.persistence.dao;

import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestVacationDao extends JpaRepository<RequestVacationEntity,Integer> {

    //Query para mostrar una solicitud por el usuario
    @Query(value = "SELECT * FROM solicitud_vaciones " +
            "WHERE nm_id_solicitud=?1 AND nm_id_usuario=?2",nativeQuery = true)
    List<RequestVacationEntity> findRequestVacationByIdAndUser(Integer nmIdRequest,Integer nmIdUser);

    //Query para mostrar todas las solicitudes del usuario
    @Query(value = "SELECT * FROM solicitud_vaciones " +
            "WHERE nm_id_usuario=?1 " +
            "ORDER BY fe_fecha_creacion",nativeQuery = true)
    List<RequestVacationEntity> findRequestVacationByUser(Integer nmIdUser);
}

