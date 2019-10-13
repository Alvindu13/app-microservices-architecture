package com.rattrapage.microserviceapi.persist.repositories;

import com.rattrapage.microserviceapi.persist.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepository extends JpaRepository<Users, Integer> {
}
