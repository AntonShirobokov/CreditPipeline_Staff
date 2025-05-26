package com.shirobokov.creditpipelinestaff.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shirobokov.creditpipelinestaff.entity.Application;
import com.shirobokov.creditpipelinestaff.entity.Passport;
import com.shirobokov.creditpipelinestaff.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserViewDTO {

    private int id;

    private String phone;

    private String lastName;

    private String firstName;

    private String middleName;

    private String email;

    private PassportViewDTO passport;

    @Override
    public String toString() {
        return "UserViewDTO{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", passport=" + passport +
                '}';
    }
}
