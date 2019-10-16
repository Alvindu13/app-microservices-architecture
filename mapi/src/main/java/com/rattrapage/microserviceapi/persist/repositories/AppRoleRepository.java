/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.rattrapage.microserviceapi.persist.repositories;

import com.rattrapage.microserviceapi.persist.models.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface App role repository.
 */
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    /**
     * Find by role name app role.
     *
     * @param roleName the role name
     * @return the app role
     */
    AppRole findByRoleName(String roleName);

}


