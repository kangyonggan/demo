package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import com.kangyonggan.demo.util.Digests;
import com.kangyonggan.demo.util.Encodes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 处理登录、登出相关请求
 *
 * @author kangyonggan
 * @since 2019-03-26
 */
@RestController
@Log4j2
@Api(tags = "LoginController", description = "登录相关接口")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping("login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "电子邮箱", example = "admin@kangyonggan.com"),
            @ApiImplicitParam(name = "password", value = "密码", example = "11111111")
    })
    public Response login(@RequestParam String email, @RequestParam String password) {
        Response response = successResponse();
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return response.failure("电子邮箱不存在");
        }
        if (user.getIsDeleted() == 1) {
            return response.failure("电子邮箱已被锁定");
        }

        byte[] salt = Encodes.decodeHex(user.getSalt());
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, AppConstants.HASH_INTERATIONS);
        String targetPassword = Encodes.encodeHex(hashPassword);
        if (!user.getPassword().equals(targetPassword)) {
            log.error("密码错误, ip:{}, email:{}", getIpAddress(), email);
            return response.failure("密码错误");
        }

        // 把登录信息放入session
        HttpSession session = ParamsInterceptor.getSession();
        session.setAttribute(AppConstants.KEY_SESSION_USER, user);
        log.info("登录成功,sessionId:{}", session.getId());
        return response;
    }

    /**
     * 注销
     *
     * @return
     */
    @GetMapping("logout")
    @ApiOperation("登出")
    public Response logout() {
        HttpSession session = ParamsInterceptor.getSession();
        log.info("登出:{}", session.getAttribute(AppConstants.KEY_SESSION_USER));
        session.removeAttribute(AppConstants.KEY_SESSION_USER);
        return successResponse();
    }

}
