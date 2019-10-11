package com.rattrapage.microserviceapi.persist.repositories;


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

    public FileController(FileRepository filesRepo, FileContentStore contentStore) {
        this.filesRepo = filesRepo;
        this.contentStore = contentStore;
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
        return null;
    }


    @PostMapping("/file")
    public ResponseEntity<?> createContent(@RequestParam("file") MultipartFile file)
            throws IOException {
        FileApp newFileApp = new FileApp();
        newFileApp.setMimeType(file.getContentType());
        contentStore.setContent(newFileApp, file.getInputStream());
        filesRepo.save(newFileApp);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/user/{id}/file")
    private ResponseEntity<?> create(@RequestBody FileApp file){
        // save to database
        FileApp newFileApp = filesRepo.save(file);
        return new ResponseEntity<FileApp>(newFileApp, HttpStatus.CREATED);
    }
}
