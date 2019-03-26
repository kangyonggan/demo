package com.kangyonggan.demo;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.demo.dto.Params;
import com.kangyonggan.demo.dto.Query;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-03-26
 */
public class RoleServiceTest extends AbstractTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void test() {
        Params params = new Params();
        params.setPageNum(1);
        params.setPageSize(10);
        params.setQuery(new Query());
        List<Role> roles = roleService.searchRoles(params);
        logger.info(new PageInfo<>(roles));
    }

}
