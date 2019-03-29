package com.kangyonggan.demo;

import com.kangyonggan.demo.model.Menu;
import com.kangyonggan.demo.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-03-26
 */
public class MenuServiceTest extends AbstractTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void test() {
        List<Menu> menus = menuService.findMenusByUserId(1L);
        logger.info(menus);
    }

}
