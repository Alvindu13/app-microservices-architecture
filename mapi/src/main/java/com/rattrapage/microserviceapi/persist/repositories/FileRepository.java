package com.rattrapage.microserviceapi.persist.repositories;

import com.rattrapage.microserviceapi.persist.models.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileRepository extends JpaRepository<Files, Integer> {

    Files findByName(String fileName);

    List<Files> findAllByUsersId(Integer userId);

}
