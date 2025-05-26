package com.shirobokov.creditpipelinestaff.restcontroller;


import com.shirobokov.creditpipelinestaff.config.security.EmployeeDetails;
import com.shirobokov.creditpipelinestaff.dto.ApplicationViewDTO;
import com.shirobokov.creditpipelinestaff.entity.Application;
import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.mapper.ApplicationMapper;
import com.shirobokov.creditpipelinestaff.service.ApplicationService;
import com.shirobokov.creditpipelinestaff.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EvaluateController {

    private final ApplicationService applicationService;

    private final EmployeeService employeeService;

    private final ApplicationMapper applicationMapper;

    public EvaluateController(ApplicationService applicationService, EmployeeService employeeService, ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.employeeService = employeeService;
        this.applicationMapper = applicationMapper;
    }


    @GetMapping("/api/evaluate/getAllUnresolvedApplication")
    public ResponseEntity<?> getAllApplication() {

        List<Application> allUnresolvedApplication = applicationService.getAllUnresolvedApplication();

        List<ApplicationViewDTO> allUnresolvedApplicationViewDTO = allUnresolvedApplication.stream().map(applicationMapper::applicationToApplicationViewDTO).collect(Collectors.toList());

        return ResponseEntity.ok(allUnresolvedApplicationViewDTO);
    }

    @PatchMapping("/api/evaluate/approveApplication")
    public ResponseEntity<?> approveApplication(@RequestBody Map<String, Object> applicationParams) {

        int applicationId = (int) applicationParams.get("applicationId");
        Number rate = (Number) applicationParams.get("percentageRate");
        Float percentageRate = rate != null ? rate.floatValue() : null;

        Employee employee = getCurrentEmployee();

        applicationService.approveApplication(applicationId, employee, percentageRate);

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/api/evaluate/denyApplication")
    public ResponseEntity<?> denyApplication(@RequestBody Map<String, Object> applicationParams) {
        int applicationId = (int) applicationParams.get("applicationId");
        String reasonForRefusal = (String) applicationParams.get("reasonForRefusal");
        Employee employee = getCurrentEmployee();

        applicationService.denyApplication(applicationId, employee, reasonForRefusal);

        return ResponseEntity.ok(null);
    }



    public Employee getCurrentEmployee() {
        EmployeeDetails employeeDetails = (EmployeeDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = employeeDetails.getUsername();

        System.out.println("Попытка получить employee моим способом: " + username);

        return employeeService.findEmployeeByUsername(username);

    }
}
