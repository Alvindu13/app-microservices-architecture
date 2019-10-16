package com.rattrapage.microserviceapi;

import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.storage.FileStorageProperties;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.Date;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class MicroserviceApiApplication {


    @Autowired
    FileContentStore fileContentStore;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApiApplication.class, args);
    }


    @Bean
    CommandLineRunner start(UserAppRepository userAppRepository,
                            FileRepository fileRepository){

        return args -> {

            File file1 = new File("storage/fileToSave.md");
            File file2 = new File("storage/fileToSave.md");



            BufferedInputStream bis = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(file1)));

            BufferedInputStream bis2 = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(file2)));


            Users user = new Users();
            user.setUsername("Roger");
            user.setEmail("alvin@hotmail.fr");
            user.setCreatedDate(new Date());
            user.setPassword("1234");
            user.setPseudo("joh");

            Files newFiles = new Files();
            newFiles.setName("HK");
            newFiles.setPath(file1.getPath());
            Files newFiles2 = new Files();
            newFiles2.setName("HK2");
            newFiles2.setPath(file2.getPath());

            fileContentStore.setContent(newFiles, bis);
            fileContentStore.setContent(newFiles2, bis2);


            user.addFile(newFiles);
            user.addFile(newFiles2);


            userAppRepository.save(user);
        };
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
