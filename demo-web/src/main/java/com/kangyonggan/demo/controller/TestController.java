package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 2019/4/15 0015
 */
@RestController
@RequestMapping("test")
@Api(tags = "TestController", description = "测试相关接口")
public class TestController extends BaseController {

    /**
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation("测试@RequestBody")
    public Response login(@RequestBody User user) {
        Response response = successResponse();

        response.put("user", user);
        return response;
    }

}
