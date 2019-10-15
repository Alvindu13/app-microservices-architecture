package com.rattrapage.microserviceapi.controllers;


import com.rattrapage.microserviceapi.exceptions.FileAppNotFoundException;
import com.rattrapage.microserviceapi.exceptions.UserNotFoundException;
import com.rattrapage.microserviceapi.notifications.Message;
import com.rattrapage.microserviceapi.notifications.MessageSource;
import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.svc.contracts.FileAppService;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("Duplicates")
@RestController
@EnableBinding(MessageSource.class)

public class FileController {


    private static final Logger logger = Logger.getLogger(FileController.class.getName());

    private FileRepository filesRepo;
    private FileContentStore contentStore;
    private UserAppRepository userAppRepository;
    private FileAppService fileAppService;

    @Autowired
    private MessageSource source;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public FileController(FileRepository filesRepo, FileContentStore contentStore, UserAppRepository userAppRepository, FileAppService fileAppService) {
        this.filesRepo = filesRepo;
        this.contentStore = contentStore;
        this.userAppRepository = userAppRepository;
        this.fileAppService = fileAppService;
    }


    @PostMapping("/user/{id}/file")
    public ResponseEntity<?> createContent(@RequestParam("name") String name,
                                           @RequestParam("file") MultipartFile file,
                                           @PathVariable Integer id) throws IOException {
        Optional<Users> user = userAppRepository.findById(id);
        Users userNewFile;
        if(user.isPresent()){
            Files newFiles = new Files();
            contentStore.setContent(newFiles, file.getInputStream());
            newFiles.setName(name);
            newFiles.setMimeType(file.getContentType());
            userNewFile = user.get();
            userNewFile.addFile(newFiles);
            userAppRepository.save(userNewFile);

            //Génère une message et le dispatch a l'ensemble des services
            Message message = new Message("Le fichier " + file.getOriginalFilename() + " a été créé");
            source.fileMessage().send(org.springframework.integration.support.MessageBuilder.withPayload(message).build());
            System.out.println(message.getMessage());

            return new ResponseEntity<>(newFiles, HttpStatus.CREATED);
        } throw new UserNotFoundException("l'user avec l'id : " + id + "n'existe pas");
    }


    @GetMapping("/user/{id}/file")
    public ResponseEntity<List<Files>> getContents(@PathVariable Integer id)
            throws IOException {
        Optional<Users> userAppOptional = userAppRepository.findById(id);
        List<Files> files;
        if(userAppOptional.isPresent()){
            files = new ArrayList<>(userAppOptional
                    .get().getFiles());
            if (files.isEmpty()) throw new FileAppNotFoundException("l'user avec l'id : " + id + " ne possède aucun fichier");
            return new ResponseEntity<>(files, HttpStatus.CREATED);
        } throw new UserNotFoundException("l'user avec l'id : " + id + "n'existe pas");
    }


    @PutMapping("/file/{id}")
    public ResponseEntity<?> updateContent(@RequestParam("name") String name,
                                           @RequestParam("file") MultipartFile file,
                                           @PathVariable Integer id)
            throws IOException {
        Optional<Files> pFileApp = filesRepo.findById(id);
        Files updateFiles;
        if(pFileApp.isPresent()){
            updateFiles = pFileApp.get();
            updateFiles.setName(name);
            updateFiles.setMimeType(file.getContentType());
            contentStore.setContent(updateFiles, file.getInputStream());
            filesRepo.save(updateFiles);

            //Génère une message et le dispatch a l'ensemble des services
            Message message = new Message("Le fichier " + file.getOriginalFilename() + " a été modifié");
            source.fileMessage().send(org.springframework.integration.support.MessageBuilder.withPayload(message).build());
            System.out.println(message.getMessage());

            return new ResponseEntity<>(updateFiles, HttpStatus.CREATED);
        } throw new FileAppNotFoundException("le fichier avec l'id " + id + " n'existe pas");
    }


    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Integer id) throws IOException {
        Optional<Files> pFileApp = filesRepo.findById(id);
        Files deleteFile;
        if(pFileApp.isPresent()){
            deleteFile = pFileApp.get();
            filesRepo.delete(deleteFile);
            //Génère une message et le dispatch a l'ensemble des services
            Message message = new Message("Le fichier " + deleteFile.getName() + " a été supprimé");
            source.fileMessage().send(org.springframework.integration.support.MessageBuilder.withPayload(message).build());
            System.out.println(message.getMessage());
            return new ResponseEntity<>( "Le fichier avec l'id " + id + " a bien été supprimé", HttpStatus.NO_CONTENT);
        } throw new FileAppNotFoundException("le fichier avec l'id " + id + " n'existe pas");
    }
}
