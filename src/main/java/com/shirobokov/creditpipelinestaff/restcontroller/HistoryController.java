package com.shirobokov.creditpipelinestaff.restcontroller;


import com.shirobokov.creditpipelinestaff.config.security.EmployeeDetails;
import com.shirobokov.creditpipelinestaff.dto.ApplicationViewDTO;
import com.shirobokov.creditpipelinestaff.entity.Application;
import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.mapper.ApplicationMapper;
import com.shirobokov.creditpipelinestaff.repository.ApplicationRepository;
import com.shirobokov.creditpipelinestaff.service.ApplicationService;
import com.shirobokov.creditpipelinestaff.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HistoryController {

    private final ApplicationService applicationService;

    private final EmployeeService employeeService;

    private final ApplicationMapper applicationMapper;


    public HistoryController(ApplicationService applicationService, EmployeeService employeeService, ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.employeeService = employeeService;
        this.applicationMapper = applicationMapper;
    }

    @GetMapping("/api/history/getAllEmployeeApplication")
    public ResponseEntity<?> getAllApplication() {

        List<Application> allUnresolvedApplication = getCurrentEmployee().getApplications();

        List<ApplicationViewDTO> allUnresolvedApplicationViewDTO = allUnresolvedApplication.stream().map(applicationMapper::applicationToApplicationViewDTO).collect(Collectors.toList());

        return ResponseEntity.ok(allUnresolvedApplicationViewDTO);
    }

    public Employee getCurrentEmployee() {
        EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = employeeDetails.getUsername();

        System.out.println("Попытка получить employee моим способом: " + username);

        return employeeService.findEmployeeByUsername(username);

    }
}
