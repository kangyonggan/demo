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
     * 查找所有用户
     *
     * @return
     */
    List<User> findAllUsers();

    /**
     * 新增用户
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param userId
     */
    void deleteUser(Long userId);

}
