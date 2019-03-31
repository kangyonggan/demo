package com.kangyonggan.demo.service;

import com.kangyonggan.demo.model.Role;

import java.util.List;

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

    /**
     * 删除用户的所有角色
     *
     * @param userId
     */
    void deleteAllRolesByUserId(Long userId);

    /**
     * 查找所有角色
     *
     * @return
     */
    List<Role> findAllRoles();

    /**
     * 查找用户所有角色
     *
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(Long userId);
}
