/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.rattrapage.microserviceapi.security.jwt;

/**
 * The interface Security params.
 */
public interface SecurityParams {
    /**
     * The constant JWT_HEADER_NAME.
     */
    public static final String JWT_HEADER_NAME = "Authorization";
    /**
     * The constant EXPIRATION.
     */
    public static final long EXPIRATION = 864000000;

    /**
     * The constant EXPIRATION.
     */
    public static final String JWT_SECRET = "BRIAN";
    /**
     * The constant HEADER_PREFIX.
     */
    public static final String HEADER_PREFIX = "Bearer ";
}
