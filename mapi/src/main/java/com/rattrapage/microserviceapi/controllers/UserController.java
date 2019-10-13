package com.rattrapage.microserviceapi.controllers;

import com.rattrapage.microserviceapi.DTO.UserAppDTO;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.utils.UserAppMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
//@RequiredArgsConstructor

@RestController
public class UserController {

    private UserAppRepository userAppRepository;
    private UserAppMapper userAppMapper;

    public UserController(UserAppRepository userAppRepository, UserAppMapper userAppMapper) {
        this.userAppRepository = userAppRepository;
        this.userAppMapper = userAppMapper;
    }

    @GetMapping("/users")
    private ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<List<Users>>(userAppRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Créer un utilisateur
     * @return
     */
    @PostMapping("/user")
    private ResponseEntity<?> create(@RequestBody Users users) throws URISyntaxException {
            // save to database
            Users newUsers = userAppRepository.save(users);
            return new ResponseEntity<>(newUsers, HttpStatus.CREATED);
    }

    /**
     * Modifier un utilisateur
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/user/{id}")
    private ResponseEntity<UserAppDTO> update(@RequestBody UserAppDTO userAppDTO, @PathVariable Integer id) throws URISyntaxException, ParseException {
        Users users = userAppMapper.toUserApp(userAppDTO);
        users.setId(id);
        userAppRepository.save(users);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userAppDTO);
    }


    /**
     * Supprimer un utilisateur
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/user/{id}")
    private ResponseEntity<?> delete(@PathVariable Integer id) throws URISyntaxException {

        Optional<Users> userAppSaved = userAppRepository.findById(id);

        if(userAppSaved.isPresent()){
            userAppRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        /*return deletedConfig == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }


}
