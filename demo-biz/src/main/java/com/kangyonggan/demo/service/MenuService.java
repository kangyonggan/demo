package com.kangyonggan.demo.service;


import com.kangyonggan.demo.model.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
public interface MenuService {

    /**
     * 判断用户是否拥有某菜单
     *
     * @param userId
     * @param menuCodes
     * @return
     */
    boolean hasMenus(Long userId, String... menuCodes);

    /**
     * 查找用户菜单
     *
     * @param userId
     * @return
     */
    List<Menu> findMenusByUserId(Long userId);

    /**
     * 查询全部菜单
     *
     * @return
     */
    List<Menu> findAllMenus();

    /**
     * 获取角色菜单
     *
     * @param roleId
     * @return
     */
    List<Menu> findMenusByRoleId(Long roleId);

    /**
     * 删除角色所有菜单
     *
     * @param roleId
     */
    void deleteAllMenusByRoleId(Long roleId);

}
