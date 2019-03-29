package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.mapper.MenuMapper;
import com.kangyonggan.demo.model.Menu;
import com.kangyonggan.demo.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Menu> findMenusByUserId(Long userId) {
        List<Menu> menus = menuMapper.selectMenusByUserId(userId);

        return recursionList(menus, new ArrayList<>(), StringUtils.EMPTY);
    }

    /**
     * 递归找出 parentCode 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param parentCode
     * @return
     */
    private List<Menu> recursionList(List<Menu> from, List<Menu> toList, String parentCode) {
        if (CollectionUtils.isEmpty(from)) {
            return toList;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (parentCode.equals(menu.getParentCode())) {
                List<Menu> leaf = new ArrayList<>();
                menu.setChildren(leaf);
                toList.add(menu);
                recursionList(from, leaf, menu.getMenuCode());
            }
        }

        return toList;
    }

}
