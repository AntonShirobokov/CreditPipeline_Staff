package com.shirobokov.creditpipelinestaff.service;


import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee findEmployeeByUsername(String username){
        return employeeRepository.findByUsername(username).orElse(null);
    }

    public boolean registrationEmployee(Employee employee){
        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()){
            return false;
        }
        saveEmployee(employee);

        return true;
    }

    public void saveEmployee(Employee employee){
        employee.setRole("ROLE_EMPLOYEE");
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
    }
}
