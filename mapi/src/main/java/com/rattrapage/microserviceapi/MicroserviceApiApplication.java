package com.rattrapage.microserviceapi;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import com.rattrapage.microserviceapi.persist.models.UserApp;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.Date;

@SpringBootApplication
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

            BufferedInputStream bis = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(
                                    new File("README.md"))));

            FileApp newFileApp = new FileApp();
            newFileApp.setName("HK");
            fileContentStore.setContent(newFileApp, bis);
            fileRepository.save(newFileApp);

            userAppRepository.save(new UserApp(
                    1,
                    "julien",
                    "julien123",
                    new Date(),
                    "julien@gmail.com",
                    "1234",
                    fileRepository.findAll()));
        };





    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
