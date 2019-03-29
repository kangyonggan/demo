package com.kangyonggan.demo.mapper;

import com.kangyonggan.demo.MyMapper;
import com.kangyonggan.demo.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 8/8/18
 */
public interface MenuMapper extends MyMapper<Menu> {

    /**
     * 判断用户是否拥有某菜单
     *
     * @param userId
     * @param menuCodes
     * @return
     */
    boolean selectExistsUserMenuCodes(@Param("userId") Long userId, @Param("menuCodes") String[] menuCodes);

    /**
     * 查找用户菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectMenusByUserId(Long userId);
}