package com.rattrapage.microserviceapi.persist.repositories;

import com.rattrapage.microserviceapi.persist.models.FileApp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileApp, Integer> {
}
