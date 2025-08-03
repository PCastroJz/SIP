package com.beto.sip.domain.employee.repository;

import java.util.List;
import java.util.Optional;

import com.beto.sip.domain.employee.Employee;

public interface EmployeeRepositoryPort {
    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();
}
