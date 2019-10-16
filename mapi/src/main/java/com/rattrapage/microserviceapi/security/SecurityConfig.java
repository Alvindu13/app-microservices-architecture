/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.rattrapage.microserviceapi.security;


import com.rattrapage.microserviceapi.security.jwt.JWTAuthenticationFilter;
import com.rattrapage.microserviceapi.security.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //Normal warning => qualifier is optional)
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Spring Security offre une API fluent pour configurer une authentification à partir de l'objet AuthenticationManagerBuilder.
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    /**
     * Cette méthode oveerride de spring permet de configurer les accès aux requêtes et de créer une session STATELESS (sans session)
     * On disable le crsf pour le token
     */
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();//obligatoire pour jwt token car empêche les requêtes en post etc
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //on utilise plus les sessions de spring (sessions de l'user en mémoire)
        http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2_console/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter( authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
    }

}

