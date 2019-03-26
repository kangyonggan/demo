package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import com.kangyonggan.demo.util.Digests;
import com.kangyonggan.demo.util.Encodes;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param session
     * @param user
     * @return
     */
    @PostMapping("login")
    public Response login(HttpSession session, User user) {
        Response response = successResponse();

        User dbUser = userService.findUserByEmail(user.getEmail());
        if (dbUser == null) {
            return response.failure("电子邮箱不存在");
        }
        if (dbUser.getIsDeleted() == 1) {
            return response.failure("电子邮箱已被锁定");
        }

        byte[] salt = Encodes.decodeHex(dbUser.getSalt());
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        String password = Encodes.encodeHex(hashPassword);
        if (!dbUser.getPassword().equals(password)) {
            log.error("密码错误, ip:{}, user:{}", getIpAddress(), user);
            return response.failure("密码错误");
        }

        // 把登录信息放入session
        session.setAttribute(AppConstants.KEY_SESSION_USER, dbUser);
        log.info("登录成功,sessionId:{}", session.getId());
        return response;
    }

    /**
     * 注销
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public Response logout(HttpSession session) {
        log.info("登出:{}", session.getAttribute(AppConstants.KEY_SESSION_USER));
        session.removeAttribute(AppConstants.KEY_SESSION_USER);
        return successResponse();
    }

}
