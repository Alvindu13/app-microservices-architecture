package com.rattrapage.microserviceapi.controllers;


import com.rattrapage.microserviceapi.storage.FileStorageService;
import com.rattrapage.microserviceapi.exceptions.FileAppNotFoundException;
import com.rattrapage.microserviceapi.exceptions.UserNotFoundException;
import com.rattrapage.microserviceapi.notifications.Message;
import com.rattrapage.microserviceapi.notifications.MessageSource;
import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("Duplicates")
@Api(value = "Handle files")
@RestController
@EnableBinding({MessageSource.class, Source.class})
public class FileController {


    private static final Logger logger = Logger.getLogger(FileController.class.getName());

    private FileRepository filesRepo;
    private FileContentStore contentStore;
    private UserAppRepository userAppRepository;
    private Source source;
    private FileStorageService fileStorageService;




    public FileController(FileRepository filesRepo, FileContentStore contentStore, UserAppRepository userAppRepository, Source source, FileStorageService fileStorageService) {
        this.filesRepo = filesRepo;
        this.contentStore = contentStore;
        this.userAppRepository = userAppRepository;
        this.source = source;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/user/{id}/file")
    @ApiOperation(value = "Create one file")
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
            //RABBITMQ Génère un message et le dispatch a l'ensemble des services
            Message message = new Message("FILE_CREATE: Le fichier " + file.getOriginalFilename() + " a été créé");
            source.output().send(MessageBuilder.withPayload(message).build());
            source.output().send(MessageBuilder.withPayload(newFiles).build());

            return new ResponseEntity<>(newFiles, HttpStatus.CREATED);
        } throw new UserNotFoundException("l'user avec l'id : " + id + "n'existe pas");
    }


    @GetMapping("/user/{id}/file")
    @ApiOperation(value = "Get all files")
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
    @ApiOperation(value = "update one file")
    public ResponseEntity<?> updateContent(@RequestParam("name") String name,
                                           @RequestParam("file") MultipartFile file,
                                           @PathVariable Integer id)
            throws IOException {
        Optional<Files> pFileApp = filesRepo.findById(id);
        Files updateFiles;
        if(pFileApp.isPresent()){

            fileStorageService.storeFile(file);
            updateFiles = pFileApp.get();
            updateFiles.setName(name);
            updateFiles.setMimeType(file.getContentType());
            contentStore.setContent(updateFiles, file.getInputStream());
            filesRepo.save(updateFiles);

            //Génère une message et le dispatch a l'ensemble des services
            Message message = new Message("FILE_UPDATE: Le fichier " + file.getOriginalFilename() + " a été modifié");
            source.output().send(MessageBuilder.withPayload(message).build());
            source.output().send(MessageBuilder.withPayload(updateFiles).build());
            System.out.println(message.getMessage());

            return new ResponseEntity<>(updateFiles, HttpStatus.CREATED);
        } throw new FileAppNotFoundException("le fichier avec l'id " + id + " n'existe pas");
    }


    @DeleteMapping("/file/{id}")
    @ApiOperation(value = "Delete one file")
    public ResponseEntity<?> deleteContent(@PathVariable Integer id) throws IOException {
        Optional<Files> pFileApp = filesRepo.findById(id);
        Files deleteFile;
        if(pFileApp.isPresent()){
            deleteFile = pFileApp.get();
            filesRepo.delete(deleteFile);
            //Génère une message et le dispatch a l'ensemble des services
            Message message = new Message("FILE_DELETE: Le fichier " + deleteFile.getName() + " a été supprimé");
            source.output().send(MessageBuilder.withPayload(message).build());
            System.out.println(message.getMessage());
            return new ResponseEntity<>( "Le fichier avec l'id " + id + " a bien été supprimé", HttpStatus.NO_CONTENT);
        } throw new FileAppNotFoundException("le fichier avec l'id " + id + " n'existe pas");
    }
}
