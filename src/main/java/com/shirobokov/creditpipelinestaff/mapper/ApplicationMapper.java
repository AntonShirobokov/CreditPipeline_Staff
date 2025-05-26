package com.shirobokov.creditpipelinestaff.mapper;


import com.shirobokov.creditpipelinestaff.dto.ApplicationViewDTO;
import com.shirobokov.creditpipelinestaff.entity.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, UserMapper.class})
public interface ApplicationMapper {

    ApplicationViewDTO applicationToApplicationViewDTO(Application application);

    Application applicationViewDTOToApplication(ApplicationViewDTO applicationViewDTO);


}
