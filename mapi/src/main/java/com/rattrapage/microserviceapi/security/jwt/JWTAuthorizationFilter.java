/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.rattrapage.microserviceapi.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The type Jwt authorization filter.
 */
@SuppressWarnings("Duplicates")
public class JWTAuthorizationFilter extends OncePerRequestFilter {



    /**
     * Spring security filter
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");

        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }

        else if(request.getRequestURI().equals("/login")){
            filterChain.doFilter(request, response);
            return;
        }

        else{
            String jwtToken = request.getHeader("Authorization");
            //System.out.println("******************* Token = " + jwtToken);
            if(jwtToken == null || !jwtToken.startsWith(SecurityParams.HEADER_PREFIX)){
                filterChain.doFilter(request, response);
                return;

            }

            DecodedJWT decodedJWT = DecodeToken.decodeJWT(request);
            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);

            //System.out.println("******************* USERNAME = " + username);
            //System.out.println("******************* ROLES = " + roles);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(rn ->{
                authorities.add(new SimpleGrantedAuthority(rn));
            });

            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(request, response);
        }

    }
}
