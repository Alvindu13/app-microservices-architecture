package com.rattrapage.microserviceapi.svc.contracts;

import com.rattrapage.microserviceapi.persist.models.Files;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileAppService {

    void saveUserToFile(Integer userId, Files newFiles);

    List<Files> getAllFilesByUsersId(Integer userId);

}
