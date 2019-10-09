package com.rattrapage.microserviceapi.controllers;


import com.rattrapage.microserviceapi.persist.models.FileApp;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private FileRepository fileRepository;


    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /*@GetMapping("/users")
    private ResponseEntity<FileApp> getOneUser(){
        return new ResponseEntity<FileApp>(userAppRepository.findAll(), HttpStatus.OK);
    }*/
}
