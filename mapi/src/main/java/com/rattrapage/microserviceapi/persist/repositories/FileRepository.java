package com.rattrapage.microserviceapi.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileApp, Integer> {
}
