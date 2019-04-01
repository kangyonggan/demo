package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.RoleService;
import com.kangyonggan.demo.service.UserService;
import com.kangyonggan.demo.util.Digests;
import com.kangyonggan.demo.util.Encodes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangyonggan
 * @since 2019/3/30 0030
 */
@RestController
@RequestMapping("validate")
@Api(tags = "ValidateController", description = "校验相关接口")
public class ValidateController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 密码校验
     *
     * @param password
     * @return
     */
    @PostMapping("password")
    @ApiOperation("密码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", required = true, example = "11111111")
    })
    @PermissionLogin
    public Response validPassword(@RequestParam String password) {
        Response response = successResponse();
        User user = ParamsInterceptor.getUser();
        byte[] salt = Encodes.decodeHex(user.getSalt());
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, AppConstants.HASH_INTERATIONS);
        String targetPassword = Encodes.encodeHex(hashPassword);
        if (!user.getPassword().equals(targetPassword)) {
            return response.failure("密码错误");
        }
        return response;
    }

    /**
     * 邮箱校验
     *
     * @param email
     * @return
     */
    @GetMapping("email")
    @ApiOperation("邮箱校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "电子邮箱", required = true, example = "admin@kangyonggan.com")
    })
    @PermissionLogin
    public Response validEmail(@RequestParam String email) {
        Response response = successResponse();
        if (userService.existsEmail(email)) {
            return response.failure("邮箱已存在");
        }
        return response;
    }

    /**
     * 角色代码校验
     *
     * @param roleCode
     * @return
     */
    @GetMapping("role")
    @ApiOperation("角色代码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色代码", required = true, example = "ROLE_TEST")
    })
    @PermissionLogin
    public Response validRole(@RequestParam String roleCode) {
        Response response = successResponse();
        if (roleService.existsRoleCode(roleCode)) {
            return response.failure("角色代码已存在");
        }
        return response;
    }

}
