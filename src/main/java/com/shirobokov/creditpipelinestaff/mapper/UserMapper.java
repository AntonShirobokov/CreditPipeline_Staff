package com.shirobokov.creditpipelinestaff.mapper;

import com.shirobokov.creditpipelinestaff.dto.UserViewDTO;
import com.shirobokov.creditpipelinestaff.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {PassportMapper.class})
public interface UserMapper {

    UserViewDTO userToUserViewDTO(User user);

    User userViewDTOToUser(UserViewDTO userViewDTO);

}
