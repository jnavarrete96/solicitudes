package com.semillero.solicitudes.persistence.dao;

import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long> {
}
