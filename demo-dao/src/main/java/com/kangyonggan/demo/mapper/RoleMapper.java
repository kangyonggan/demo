package com.kangyonggan.demo.mapper;

import com.kangyonggan.demo.MyMapper;
import com.kangyonggan.demo.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 删除用户角色
     *
     * @param userId
     */
    void deleteAllRolesByUserId(Long userId);

    /**
     * 查找用户角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

    /**
     * 删除角色权限
     *
     * @param roleId
     */
    void deleteRoleMenus(@Param("roleId") Long roleId);

    /**
     * 批量插入角色权限
     *
     * @param roleId
     * @param menuIds
     */
    void insertRoleMenus(@Param("roleId") Long roleId, @Param("menuIds") List<String> menuIds);
}