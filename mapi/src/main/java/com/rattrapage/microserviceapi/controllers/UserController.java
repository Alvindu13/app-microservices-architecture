package com.rattrapage.microserviceapi.controllers;

import com.rattrapage.microserviceapi.persist.models.UserApp;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserAppRepository userAppRepository;

    public UserController(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @GetMapping("/users")
    private ResponseEntity<List<UserApp>> getOneUser(){
        return new ResponseEntity<List<UserApp>>(userAppRepository.findAll(), HttpStatus.OK);
    }






}
