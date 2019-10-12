package com.rattrapage.microserviceapi.controllers;


import com.rattrapage.microserviceapi.persist.models.FileApp;
import com.rattrapage.microserviceapi.svc.contracts.FileAppService;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.logging.Logger;


import java.io.IOException;
import java.util.Optional;

@RestController
public class FileController {


    private static final Logger logger = Logger.getLogger(FileController.class.getName());

    private FileRepository filesRepo;
    private FileContentStore contentStore;
    private FileAppService fileAppService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public FileController(FileRepository filesRepo, FileContentStore contentStore, FileAppService fileAppService) {
        this.filesRepo = filesRepo;
        this.contentStore = contentStore;
        this.fileAppService = fileAppService;
    }

    @RequestMapping(value="/files/{fileId}", method = RequestMethod.PUT)
    public ResponseEntity<?> setContent(@PathVariable("fileId") Long id, @RequestParam("file") MultipartFile file)
            throws IOException {
        Optional<FileApp> f = filesRepo.findById(Math.toIntExact(id));
        if (f.isPresent()) {
            f.get().setMimeType(file.getContentType());
            contentStore.setContent(f.get(), file.getInputStream());
            // save updated content-related info
            filesRepo.save(f.get());
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.CONFLICT);
    }


    @PostMapping("/user/{id}/file")
    public ResponseEntity<?> createContent(@RequestParam("name") String name,
                                           @RequestParam("file") MultipartFile file,
                                           @PathVariable Integer id)
            throws IOException {
        FileApp newFileApp = new FileApp();
        newFileApp.setName(name);
        newFileApp.setMimeType(file.getContentType());
        contentStore.setContent(newFileApp, file.getInputStream());
        fileAppService.saveUserToFile(id, newFileApp);
        return new ResponseEntity<>(newFileApp, HttpStatus.CREATED);
    }


    /*@GetMapping("/user/{id}/file")
    public ResponseEntity<?> getContents(@PathVariable String id)
            throws IOException {
        FileApp newFileApp = new FileApp();
        newFileApp.setName(name);
        newFileApp.setMimeType(file.getContentType());
        contentStore.setContent(newFileApp, file.getInputStream());
        filesRepo.save(newFileApp);
        return new ResponseEntity<>(newFileApp, HttpStatus.CREATED);
    }*/



}
