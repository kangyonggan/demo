package com.kangyonggan.demo.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.demo.annotation.PermissionRole;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2019/1/5 0005
 */
@RestController
@RequestMapping("terminal")
@Log4j2
public class TerminalController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 测试
     *
     * @return
     */
    @GetMapping("test")
    @PermissionRole("ROLE_ADMIN")
    public Response test() {
        log.info("测试一下");
        List<Role> roles = roleService.searchRoles(getRequestParams());
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        boolean hasRoles = roleService.hasRoles(1L, "ROLE_ADMIN");
        log.info("hasRoles:{}", hasRoles);

        Response response = Response.getSuccessResponse();
        response.putAll(getQuery());
        response.put("now", new Date());
        response.put("pageInfo", pageInfo);
        return response;
    }

}
