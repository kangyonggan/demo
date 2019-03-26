package com.kangyonggan.demo.service;


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

}
