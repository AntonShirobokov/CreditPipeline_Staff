package com.shirobokov.creditpipelinestaff.dto;


import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationViewDTO {

    private int id;

    private UserViewDTO user;

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

    private String reasonForRefusal;

    private Boolean wasBankrupt;

    private Integer realIncome;

    private LocalDateTime dateOfCreation;

    private Float score;

    private Float percentageRate;

    private LocalDateTime dateOfReview;

    @Override
    public String toString() {
        return "ApplicationViewDTO{" +
                "id=" + id +
                ", user=" + user +
                ", employee=" + employee +
                ", amount=" + amount +
                ", period=" + period +
                ", purpose='" + purpose + '\'' +
                ", age=" + age +
                ", typeOfEmployment='" + typeOfEmployment + '\'' +
                ", deposit='" + deposit + '\'' +
                ", income=" + income +
                ", dept=" + dept +
                ", education='" + education + '\'' +
                ", typeOfHousing='" + typeOfHousing + '\'' +
                ", numberOfDependents=" + numberOfDependents +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", status='" + status + '\'' +
                ", reasonForRefusal='" + reasonForRefusal + '\'' +
                ", wasBankrupt=" + wasBankrupt +
                ", realIncome=" + realIncome +
                ", dateOfCreation=" + dateOfCreation +
                ", score=" + score +
                ", percentageRate=" + percentageRate +
                ", dateOfReview=" + dateOfReview +
                '}';
    }
}
