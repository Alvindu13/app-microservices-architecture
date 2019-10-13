package com.rattrapage.microserviceapi.svc.impl;

import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.svc.contracts.FileAppService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void saveUserToFile(Integer userId, Files files){
        Optional<Users> userAppOptional = userAppRepository.findById(userId);
        Users users = new Users();
        if(userAppOptional.isPresent()){
            users = userAppOptional.get();
        }
        users.getFiles().add(files);
        userAppRepository.save(users);
    }

    @Override
    public List<Files> getAllFilesByUsersId(Integer userId) {

        return fileRepository.findAllByUsersId(userId);
    }
}
