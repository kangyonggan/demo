package com.kangyonggan.demo.mapper;

import com.kangyonggan.demo.MyMapper;
import com.kangyonggan.demo.model.Role;
import org.apache.ibatis.annotations.Param;

/**
 * @author kangyonggan
 * @since 8/8/18
 */
public interface RoleMapper extends MyMapper<Role> {

    /**
     * 判断用户是否拥有某角色
     *
     * @param userId
     * @param roleCodes
     * @return
     */
    boolean selectExistsUserRoleCode(@Param("userId") Long userId, @Param("roleCodes") String[] roleCodes);

}