package com.shirobokov.creditpipelinestaff.dto;

import com.shirobokov.creditpipelinestaff.entity.Application;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeViewDTO {

    private int id;

    private String username;

    @Override
    public String toString() {
        return "EmployeeViewDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
