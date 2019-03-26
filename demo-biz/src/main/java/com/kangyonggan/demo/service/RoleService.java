package com.kangyonggan.demo.service;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
public interface RoleService {

    /**
     * 判断用户是否拥有某角色
     *
     * @param userId
     * @param roleCodes
     * @return
     */
    boolean hasRoles(Long userId, String... roleCodes);

}
