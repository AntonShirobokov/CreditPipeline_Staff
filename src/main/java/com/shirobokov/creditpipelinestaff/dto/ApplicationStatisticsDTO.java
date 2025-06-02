package com.shirobokov.creditpipelinestaff.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationStatisticsDTO {
    private int id;

    private EmployeeViewDTO employee;

    private Integer amount;

    private Integer period;

    private String purpose;

    private Integer age;

    private String typeOfEmployment;

    private String deposit;

    private Integer income;

    private Integer dept;

    private String education;

    private String typeOfHousing;

    private Integer numberOfDependents;

    private String maritalStatus;

    private String status;

    private LocalDateTime dateOfCreation;

    private LocalDateTime dateOfReview;
}
