package com.shirobokov.creditpipelinestaff.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "t_reject_rule")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="field_name")
    private String fieldName;

    @Column(name="operation")
    private String operation;

    @Column(name="value")
    private String value;

    @Column(name="reason")
    private String reason;
}
