package com.kangyonggan.demo.mapper;

import com.kangyonggan.demo.MyMapper;
import com.kangyonggan.demo.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author kangyonggan
 * @since 8/8/18
 */
public interface UserMapper extends MyMapper<User> {

    /**
     * 添加用户角色
     *
     * @param userId
     * @param roleIds
     */
    void insertUserRoles(@Param("userId") Long userId, @Param("roleIds") String[] roleIds);
}