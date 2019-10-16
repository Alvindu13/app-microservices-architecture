package com.rattrapage.microserviceapi;

import com.rattrapage.microserviceapi.persist.models.AppRole;
import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.security.AccountService;
import com.rattrapage.microserviceapi.storage.FileStorageProperties;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.*;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class MicroserviceApiApplication {


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    FileContentStore fileContentStore;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApiApplication.class, args);
    }



    @Bean
    CommandLineRunner start(AccountService accountService,
                            UserAppRepository userAppRepository,
                            FileRepository fileRepository){

        return args -> {



            accountService.saveRole((new AppRole(null, "USER")));
            accountService.saveRole((new AppRole(null, "ADMIN")));


            File file1 = new File("storage/fileToSave.md");
            File file2 = new File("storage/fileToSave.md");

            BufferedInputStream bis = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(file1)));

            BufferedInputStream bis2 = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(file2)));




            Files newFiles = new Files();
            newFiles.setName("HK");
            newFiles.setPath(file1.getPath());
            Files newFiles2 = new Files();
            newFiles2.setName("HK2");
            newFiles2.setPath(file2.getPath());


            fileContentStore.setContent(newFiles, bis);
            fileContentStore.setContent(newFiles2, bis2);



            Users user = new Users();
            user.setUsername("brian");
            user.setEmail("admin@gmail.fr");
            user.setPassword(bCryptPasswordEncoder.encode("1234"));
            user.setConfirmedPassword("1234");
            user.setPseudo("rodeo");

            user.addFile(newFiles);
            user.addFile(newFiles2);

            userAppRepository.save(user);

            accountService.addRoleToUser("brian", "ADMIN");

            //userAppRepository.save(user);

        };
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
