/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.rattrapage.microserviceapi.security;


import com.rattrapage.microserviceapi.persist.models.AppRole;
import com.rattrapage.microserviceapi.persist.models.Files;
import com.rattrapage.microserviceapi.persist.models.Users;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The interface Account service.
 */
public interface AccountService {

    /**
     * Save user app user.
     *
     * @param username          the username
     * @param password          the password
     * @param confirmedPassword the confirmed password
     * @param files
     * @return the app user
     */

    Users saveUser(String pseudo, String username, String password, String confirmedPassword, List<Files> files) throws FileNotFoundException;

    /**
     * Save role app role.
     *
     * @param appRole the app role
     * @return the app role
     */
    AppRole saveRole(AppRole appRole);

    /**
     * Load user by username app user.
     *
     * @param username the username
     * @return the app user
     */
    Users loadUserByUsername(String username);

    /**
     * Add role to user.
     *
     * @param username the username
     * @param roleName the role name
     */
    void addRoleToUser(String username, String roleName);

    void addFileToUser(String username, Files files);
}
