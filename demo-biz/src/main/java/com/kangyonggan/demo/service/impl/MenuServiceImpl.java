package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.mapper.MenuMapper;
import com.kangyonggan.demo.model.Menu;
import com.kangyonggan.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public boolean hasMenus(Long userId, String[] menuCodes) {
        return menuMapper.selectExistsUserMenuCodes(userId, menuCodes);
    }

}
