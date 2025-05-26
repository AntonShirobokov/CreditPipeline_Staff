package com.shirobokov.creditpipelinestaff.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PassportViewDTO {

    private int id;

    private String series;

    private String number;

    private LocalDate birthDate;

    private String birthPlace;

    private String departmentCode;

    private String issuedBy;

    private LocalDate issueDate;

    private String inn;

    private String snils;

    private String registrationAddress;

    @Override
    public String toString() {
        return "PassportViewDTO{" +
                "id=" + id +
                ", series='" + series + '\'' +
                ", number='" + number + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", issueDate=" + issueDate +
                ", inn='" + inn + '\'' +
                ", snils='" + snils + '\'' +
                ", registrationAddress='" + registrationAddress + '\'' +
                '}';
    }
}
