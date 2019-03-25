package com.kangyonggan.demo.service;

import com.kangyonggan.demo.model.User;

import java.util.List;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
public interface UserService {

    /**
     * 查找用户
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * 判断电子邮箱是否存在
     *
     * @param email
     * @return
     */
    boolean existsEmail(String email);

    /**
     * 查找用户
     *
     * @param userId
     * @return
     */
    User findUserByUserId(Long userId);

    /**
     * /**
     * 批量删除用户
     *
     * @param userIds
     */
    void deleteUsers(String userIds);

    /**
     * 批量恢复用户
     *
     * @param userIds
     */
    void restoreUsers(String userIds);

    /**
     * 更新密码
     *
     * @param user
     */
    void updateUserPassword(User user);

    /**
     * 更新授权角色
     *
     * @param userId
     * @param roleIds
     */
    void updateUserRoles(Long userId, String roleIds);

    /**
     * 查看授权用户
     *
     * @param roleId
     * @return
     */
    List<User> findUsersByRoleId(Long roleId);
}
