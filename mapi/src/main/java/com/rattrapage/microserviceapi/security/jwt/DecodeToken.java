/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.rattrapage.microserviceapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Decode token.
 */
public class DecodeToken {

    /**
     * Decode jwt username.
     *
     * @param request the request
     * @return the username string
     */
    public static DecodedJWT decodeJWT(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.JWT_SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(jwtToken.substring(SecurityParams.HEADER_PREFIX.length()));
        return decodedJWT;
    }
}
