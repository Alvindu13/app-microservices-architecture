package com.rattrapage.microserviceapi.svc.impl;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import com.rattrapage.microserviceapi.persist.models.UserApp;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.svc.contracts.FileAppService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FileAppServiceImpl implements FileAppService {

    private UserAppRepository userAppRepository;
    private FileRepository fileRepository;

    public FileAppServiceImpl(UserAppRepository userAppRepository, FileRepository fileRepository) {
        this.userAppRepository = userAppRepository;
        this.fileRepository = fileRepository;
    }

    @SuppressWarnings("Duplicates")
    public void saveUserToFile(Integer userId, FileApp fileApp){
        Optional<UserApp> userAppOptional = userAppRepository.findById(userId);
        UserApp userApp = new UserApp();
        if(userAppOptional.isPresent()){
            userApp = userAppOptional.get();
        }
        userApp.getFileApps().add(fileApp);
        userAppRepository.save(userApp);
    }
}
