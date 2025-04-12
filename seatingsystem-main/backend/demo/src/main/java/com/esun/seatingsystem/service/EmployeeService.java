package com.esun.seatingsystem.service;

import com.esun.seatingsystem.dto.EmployeeDto;
import com.esun.seatingsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeDto> getAllEmployees() {
        return repository.getAllEmployees();
    }
}