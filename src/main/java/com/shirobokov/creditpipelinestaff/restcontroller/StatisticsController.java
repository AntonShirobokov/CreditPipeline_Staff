package com.shirobokov.creditpipelinestaff.restcontroller;


import com.shirobokov.creditpipelinestaff.dto.ApplicationStatisticsDTO;
import com.shirobokov.creditpipelinestaff.mapper.ApplicationMapper;
import com.shirobokov.creditpipelinestaff.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private final ApplicationService applicationService;

    private final ApplicationMapper applicationMapper;

    public StatisticsController(ApplicationService applicationService, ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
    }

    @GetMapping("/api/statistics/getApplication")
    @ResponseBody
    public ResponseEntity<?> getApplicationForStatistics() {
        List<ApplicationStatisticsDTO> applicationStatisticsList = applicationService.getAllApplication().stream().map(applicationMapper::applocationToApplicationStatisticsDTO).toList();
        return ResponseEntity.ok(applicationStatisticsList);
    }
}
