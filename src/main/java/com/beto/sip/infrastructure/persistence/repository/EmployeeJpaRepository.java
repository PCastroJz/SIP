package com.beto.sip.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beto.sip.infrastructure.persistence.entity.EmployeeEntity;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {
}
