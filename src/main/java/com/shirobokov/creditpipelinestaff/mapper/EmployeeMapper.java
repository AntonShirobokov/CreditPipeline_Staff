package com.shirobokov.creditpipelinestaff.mapper;


import com.shirobokov.creditpipelinestaff.dto.EmployeeViewDTO;
import com.shirobokov.creditpipelinestaff.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeViewDTO employeeToEmployeeViewDTO(Employee employee);

    Employee employeeViewDTOToEmployee(EmployeeViewDTO employeeViewDTO);

}
