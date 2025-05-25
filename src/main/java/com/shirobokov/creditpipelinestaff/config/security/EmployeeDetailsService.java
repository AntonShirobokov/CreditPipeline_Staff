package com.shirobokov.creditpipelinestaff.config.security;

import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.repository.EmployeeRepository;
import com.shirobokov.creditpipelinestaff.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUsername(username);

        System.out.println("На этапе входа в аккаунт: " + employee.get().getRole());

        if (employee.isPresent()) {
            return new EmployeeDetails(employee.get());
        }
        throw new UsernameNotFoundException("Неправильный логин или пароль");
    }
}
