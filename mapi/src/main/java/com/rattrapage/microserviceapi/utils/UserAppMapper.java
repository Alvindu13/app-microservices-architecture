package com.rattrapage.microserviceapi.utils;

import com.rattrapage.microserviceapi.persist.DTO.UserAppDTO;
import com.rattrapage.microserviceapi.persist.models.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserAppMapper {

    UserAppDTO toUserAppDTO(Users users);

    List<UserAppDTO> toUserAppDTOs(List<Users> users);

    Users toUserApp(UserAppDTO userAppDTO);
}
