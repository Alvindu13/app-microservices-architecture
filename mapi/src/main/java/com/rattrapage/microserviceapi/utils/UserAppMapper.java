package com.rattrapage.microserviceapi.utils;

import com.rattrapage.microserviceapi.DTO.UserAppDTO;
import com.rattrapage.microserviceapi.persist.models.UserApp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserAppMapper {

    UserAppDTO toUserAppDTO(UserApp userApp);

    List<UserAppDTO> toUserAppDTOs(List<UserApp> userApps);

    UserApp toUserApp(UserAppDTO userAppDTO);
}
