package com.shirobokov.creditpipelinestaff.restcontroller;


import com.shirobokov.creditpipelinestaff.dto.ApplicationStatisticsDTO;
import com.shirobokov.creditpipelinestaff.dto.ApplicationViewDTO;
import com.shirobokov.creditpipelinestaff.entity.Application;
import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.mapper.ApplicationMapper;
import com.shirobokov.creditpipelinestaff.service.ApplicationService;
import com.shirobokov.creditpipelinestaff.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private final ApplicationService applicationService;

    private final EmployeeService employeeService;

    private final ApplicationMapper applicationMapper;

    public StatisticsController(ApplicationService applicationService, EmployeeService employeeService, ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.employeeService = employeeService;
        this.applicationMapper = applicationMapper;
    }

    @GetMapping("/api/statistics/getApplication")
    @ResponseBody
    public ResponseEntity<?> getApplicationForStatistics() {
        List<ApplicationStatisticsDTO> applicationStatisticsList = applicationService.getAllApplication().stream().map(applicationMapper::applocationToApplicationStatisticsDTO).toList();
        return ResponseEntity.ok(applicationStatisticsList);
    }

    @GetMapping("/api/statistics/getInfoApplication")
    public ResponseEntity<?> getInfoApplicationForStatistics(@RequestParam Integer applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            ApplicationViewDTO applicationViewDTO = applicationMapper.applicationToApplicationViewDTO(application);
            return ResponseEntity.ok(applicationViewDTO);
        }
    }

    @GetMapping("/api/statistics/getInfoEmployee")
    @ResponseBody
    public ResponseEntity<?> getInfoEmployeeForStatistics(@RequestParam String username) {
        Employee employee = employeeService.findEmployeeByUsername(username);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            List<Integer> listApplication = employee.getApplications().stream().map(Application::getId).collect(Collectors.toList());
            Map<String, Object> map = new HashMap<>();
            map.put("id", employee.getId());
            map.put("username", username);
            map.put("lastName", employee.getLastName());
            map.put("firstName", employee.getFirstName());
            map.put("middleName", employee.getMiddleName());
            map.put("listApplication", listApplication);
            return ResponseEntity.ok(map);
        }
    }

}
