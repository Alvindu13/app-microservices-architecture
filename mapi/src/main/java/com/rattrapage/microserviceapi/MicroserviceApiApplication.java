package com.rattrapage.microserviceapi;

import com.rattrapage.microserviceapi.persist.models.UserApp;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class MicroserviceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApiApplication.class, args);
    }


    @Bean
    CommandLineRunner start(UserAppRepository userAppRepository){

        return args -> {
            userAppRepository.save(new UserApp(1, "julien", "julien123", new Date(), "julien@gmail.com", "1234"));
            userAppRepository.save(new UserApp(2, "decrypt", "decrypt123", null, "decrypt@gmail.com", "1234"));
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
