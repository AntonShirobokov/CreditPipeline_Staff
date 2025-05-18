package com.shirobokov.creditpipelinestaff.service;


import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findEmployeeByUsername(String username){
        return employeeRepository.findByUsername(username).orElse(null);
    }
}
