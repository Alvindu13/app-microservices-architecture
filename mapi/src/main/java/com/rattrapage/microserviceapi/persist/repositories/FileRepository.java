package com.rattrapage.microserviceapi.persist.repositories;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileRepository extends JpaRepository<FileApp, Integer> {

    FileApp findByName(String fileName);

    List<FileApp> findAllByUserAppId(Integer userId);

}
