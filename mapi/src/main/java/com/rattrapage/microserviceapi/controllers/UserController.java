package com.rattrapage.microserviceapi.controllers;

import com.rattrapage.microserviceapi.exceptions.UserAlreadyExist;
import com.rattrapage.microserviceapi.exceptions.UserNotFoundException;
import com.rattrapage.microserviceapi.persist.DTO.UserAppDTO;
import com.rattrapage.microserviceapi.persist.models.Users;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.utils.UserAppMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "Handle Users")
public class UserController {

    private UserAppRepository userAppRepository;
    private UserAppMapper userAppMapper;

    public UserController(UserAppRepository userAppRepository, UserAppMapper userAppMapper) {
        this.userAppRepository = userAppRepository;
        this.userAppMapper = userAppMapper;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Find all users")
    private ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = new ArrayList<>();
        users = userAppRepository.findAll();
        if(!users.isEmpty()){
            return new ResponseEntity<>(userAppRepository.findAll(), HttpStatus.OK);
        } throw new UserNotFoundException("Il n'y a aucun utilisateur à retourner");
    }

    /**
     * Créer un utilisateur
     * @return
     */
    @PostMapping("/user")
    @ApiOperation(value = "Create one user")
    private ResponseEntity<?> create(@RequestBody Users users) throws URISyntaxException {
        //TODO vérifier que le password de l'user respecte bien le bon format attendu [a-zA-Z0-9_-]
        // De même pour email
        Optional<Users> pUser = userAppRepository.findById(users.getId());
        if(pUser.isPresent()) {
            // save to database
            Users newUsers = userAppRepository.save(users);
            return new ResponseEntity<>(newUsers, HttpStatus.CREATED);
        } else throw new UserAlreadyExist("L'utilisateur avec cet id existe déjà");
    }

    /**
     * Modifier un utilisateur
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Modif one user")
    @PutMapping("/user/{id}")
    private ResponseEntity<UserAppDTO> update(@RequestBody UserAppDTO userAppDTO, @PathVariable Integer id) throws URISyntaxException, ParseException {
        Optional<Users> pUser = userAppRepository.findById(id);
        if(pUser.isPresent()) {
            Users users = userAppMapper.toUserApp(userAppDTO);
            users.setId(id);
            userAppRepository.save(users);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userAppDTO);
        } else throw new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas");
    }


    /**
     * Supprimer un utilisateur
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Delete one user")
    @DeleteMapping("/user/{id}")
    private ResponseEntity<?> delete(@PathVariable Integer id) throws URISyntaxException {
        Optional<Users> userAppSaved = userAppRepository.findById(id);
        if(userAppSaved.isPresent()){
            userAppRepository.deleteById(id);
            return new ResponseEntity<>("L'utilisateur avec l'id " + id + " a bien été supprimé", HttpStatus.NO_CONTENT);
        } else throw new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas");
    }


}
