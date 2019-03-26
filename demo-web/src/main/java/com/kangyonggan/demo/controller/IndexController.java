package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.annotation.PermissionRole;
import com.kangyonggan.demo.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author kangyonggan
 * @since 12/6/18
 */
@RestController
@RequestMapping("/")
public class IndexController {

    /**
     * 测试
     *
     * @return
     */
    @GetMapping("test")
    @PermissionRole("ROLE_ADMIN")
    public Response test() {
        return Response.getSuccessResponse();
    }

}
