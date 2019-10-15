package com.rattrapage.microserviceapi;

import com.rattrapage.microserviceapi.notifications.MessageSource;
import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

            BufferedInputStream bis2 = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(
                                    new File("README.md"))));


            Users user = new Users();
            user.setUsername("Roger");
            user.setEmail("alvin@hotmail.fr");
            user.setCreatedDate(new Date());
            user.setPassword("1234");
            user.setPseudo("joh");

            Files newFiles = new Files();
            newFiles.setName("HK");
            Files newFiles2 = new Files();
            newFiles2.setName("HK2");

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
