package com.kangyonggan.demo.service.system;

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
     * @param roleIds
     */
    void updateUser(User user, String[] roleIds);

    /**
     * 校验邮箱是否存在
     *
     * @param email
     * @return
     */
    boolean existsEmail(String email);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 删除用户
     *
     * @param userId
     */
    void deleteUser(Long userId);
}
