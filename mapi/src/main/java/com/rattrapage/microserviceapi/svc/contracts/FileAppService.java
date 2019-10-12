package com.rattrapage.microserviceapi.svc.contracts;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileAppService {

    void saveUserToFile(Integer userId, FileApp newFileApp);

    List<FileApp> getAllFilesByUsersId(Integer userId);

}
