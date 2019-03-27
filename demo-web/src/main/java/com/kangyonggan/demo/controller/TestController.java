package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 2019/3/27 0027
 */
@RestController
@RequestMapping("test")
@Api(tags = "Test", description = "测试相关接口")
@Log4j2
public class TestController extends BaseController {

    @PermissionLogin
    @GetMapping
    @ApiOperation("测试@PermissionLogin")
    public Response test() {
        log.info(ParamsInterceptor.getToken());
        log.info(ParamsInterceptor.getUser());
        return successResponse();
    }

}
