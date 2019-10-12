package com.rattrapage.microserviceapi.svc.contracts;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import org.springframework.stereotype.Service;

@Service
public interface FileAppService {

    void saveUserToFile(Integer userId, FileApp newFileApp);

}
