package com.kangyonggan.demo.service;

import com.kangyonggan.demo.dto.Params;
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
     * 搜索用户
     *
     * @param params
     * @return
     */
    List<User> searchUsers(Params params);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);
}
