package com.rattrapage.microserviceapi.persist.repositories;

import com.rattrapage.microserviceapi.persist.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserAppRepository extends JpaRepository<Users, Integer> {

    /**
     * Find by username app user.
     *
     * @param username the username
     * @return the app user
     */
    Users findByUsername(@Param("username") String username);
}
