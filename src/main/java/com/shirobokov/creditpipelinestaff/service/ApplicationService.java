package com.shirobokov.creditpipelinestaff.service;


import com.shirobokov.creditpipelinestaff.entity.Application;
import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getAllUnresolvedApplication() {
        return applicationRepository.findByStatus("В рассмотрении");
    }

    public List<Application> getAllApplication() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(int id) {
        return applicationRepository.findById(id).get();
    }


    public void approveApplication(int applicationId, Employee employee, Float percentageRate) {
        Application application = applicationRepository.findById(applicationId).get();

        application.setEmployee(employee);
        application.setDateOfReview(LocalDateTime.now());
        application.setStatus("Одобрено");
        application.setPercentageRate(percentageRate);

        applicationRepository.save(application);
    }

    public void denyApplication(int applicationId, Employee employee, String reasonForRefusal) {
        Application application = applicationRepository.findById(applicationId).get();

        application.setEmployee(employee);
        application.setDateOfReview(LocalDateTime.now());
        application.setStatus("Отказано");
        application.setReasonForRefusal(reasonForRefusal);

        applicationRepository.save(application);
    }
}
