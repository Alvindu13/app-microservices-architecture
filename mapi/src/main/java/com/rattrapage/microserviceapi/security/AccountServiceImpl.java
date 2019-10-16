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
import com.rattrapage.microserviceapi.persist.repositories.AppRoleRepository;
import com.rattrapage.microserviceapi.persist.repositories.FileRepository;
import com.rattrapage.microserviceapi.persist.repositories.UserAppRepository;
import com.rattrapage.microserviceapi.utils.FileContentStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

/**
 * The type Account service.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    //idem @Autowired
    private UserAppRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private FileRepository fileRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    FileContentStore fileContentStore;

    /**
     * Instantiates a new Account service.
     *
     * @param appUserRepository     the app user repository
     * @param appRoleRepository     the app role repository
     * @param bCryptPasswordEncoder the b crypt password encoder
     */
    public AccountServiceImpl(UserAppRepository appUserRepository, AppRoleRepository appRoleRepository, FileRepository fileRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.fileRepository = fileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Save one user: AppUser.
     *
     * @param username          the username
     * @param password          the password
     * @param confirmedPassword the confirmed password
     * @param files
     * @return
     */
    @Override
    public Users saveUser(String pseudo, String username, String password, String confirmedPassword, List<Files> files) throws FileNotFoundException {

        Users user = appUserRepository.findByUsername(username);

        if(user != null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmedPassword)) throw new RuntimeException(("Please confirm your password"));

        Users appUser = new Users();
        appUser.setUsername(username);
        appUser.setActived(true);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setPseudo(pseudo);
        appUser.setFiles(files);




        appUserRepository.save(appUser);
        addRoleToUser(username, "USER");

        return appUser;
    }

    /**
     * Save roles: AppRole.
     *
     * @param appRole the app role
     * @return
     */
    @Override
    public AppRole saveRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    /**
     * Load one user: AppUser by username.
     *
     * @param username the username
     * @return
     */
    @Override
    public Users loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    /**
     * Add role(s) to user: AppUser.
     *
     * @param username the username
     * @param roleName the role name
     */
    @Override
    public void addRoleToUser(String username, String roleName) {
        Users appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public void addFileToUser(String username, Files files) {
        Users appUser = appUserRepository.findByUsername(username);
        appUser.getFiles().add(files);
    }
}
