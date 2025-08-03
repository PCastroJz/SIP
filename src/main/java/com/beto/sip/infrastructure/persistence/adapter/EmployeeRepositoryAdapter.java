package com.beto.sip.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beto.sip.domain.employee.Employee;
import com.beto.sip.domain.employee.repository.EmployeeRepositoryPort;
import com.beto.sip.infrastructure.persistence.mapper.EmployeePersistenceMapper;
import com.beto.sip.infrastructure.persistence.repository.EmployeeJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeJpaRepository jpaRepo;
    private final EmployeePersistenceMapper mapper;

    @Override
    public Employee save(Employee employee) {
        return mapper.toDomain(jpaRepo.save(mapper.toEntity(employee)));
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Employee> findAll() {
        return jpaRepo.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}
