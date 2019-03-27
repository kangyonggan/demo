package com.kangyonggan.demo.constants;

/**
 * 系统常量
 *
 * @author kangyonggan
 * @since 2019-03-25
 */
public interface AppConstants {

    /**
     * Hash Interations
     */
    int HASH_INTERATIONS = 2;

    /**
     * Salt Size
     */
    int SALT_SIZE = 8;

    /**
     * token在header中的名称
     */
    String HEADER_TOKEN_NAME = "X-Auth-Token";

    /**
     * 用户在session中的key
     */
    String KEY_SESSION_USER = "key-session-user";

}
