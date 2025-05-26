package com.shirobokov.creditpipelinestaff.mapper;

import com.shirobokov.creditpipelinestaff.dto.PassportViewDTO;
import com.shirobokov.creditpipelinestaff.entity.Passport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    PassportViewDTO passportToPassportDTO(Passport passport);

    Passport passportDTOToPassport(PassportViewDTO passportViewDTO);

}
