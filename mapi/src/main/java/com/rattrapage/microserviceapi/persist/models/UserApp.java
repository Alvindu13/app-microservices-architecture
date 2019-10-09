package com.rattrapage.microserviceapi.persist.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
//On préfère l'appeler UserApp plutôt qu'User pour éviter des conflits avec d'autres classes User
//Par défaut hibernate va créer les tables avec une convention de nommage par défaut qui est la plus couremment utilisée
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String pseudo;

    //On respecte le format attendu
    //@JsonFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdDate;

    // TODO
    //cette resource n'est visible que si l'utilisateur connecté qui souhaite accéder à l'endpoint en est le propriétaire
    private String email;

    @JsonIgnore
    private String password;

    //Getters and setters + constructor generated by lombok
}
