package com.kangyonggan.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.annotation.PermissionRole;
import com.kangyonggan.demo.constants.Resp;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.MenuService;
import com.kangyonggan.demo.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录认证、身份认证
 *
 * @author kangyonggan
 * @since 6/8/18
 */
@Log4j2
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private RoleService roleService;

    private MenuService menuService;

    public AuthInterceptor(RoleService roleService, MenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 校验登录权限注解
            if (!validLogin(response, handlerMethod)) {
                return false;
            }

            // 校验菜单权限注解
            if (!validMenu(response, handlerMethod)) {
                return false;
            }

            // 校验角色权限注解
            if (!validRole(response, handlerMethod)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 校验角色权限注解
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validRole(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionRole permissionRole = handlerMethod.getMethodAnnotation(PermissionRole.class);
        if (permissionRole != null) {
            if (!isLogin(response)) {
                return false;
            }

            User user = ParamsInterceptor.getUser();
            boolean hasRole = roleService.hasRoles(user.getUserId(), permissionRole.value());
            if (!hasRole) {
                // 9997: 权限不足
                Response resp = new Response();
                resp.failure(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验菜单权限注解
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validMenu(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionMenu permissionMenu = handlerMethod.getMethodAnnotation(PermissionMenu.class);
        if (permissionMenu != null) {
            if (!isLogin(response)) {
                return false;
            }

            User user = ParamsInterceptor.getUser();
            boolean hasMenu = menuService.hasMenus(user.getUserId(), permissionMenu.value());
            if (!hasMenu) {
                // 9997: 权限不足
                Response resp = new Response();
                resp.failure(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否登录
     *
     * @param response
     * @return
     */
    private boolean isLogin(HttpServletResponse response) {
        // 判断是否登录
        if (!ParamsInterceptor.getSession().getId().equals(ParamsInterceptor.getToken())) {
            // 9998: 登录失效
            Response resp = new Response();
            resp.failure(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
            writeResponse(response, resp);
            return false;
        }
        return true;
    }

    /**
     * 校验登录权限注解
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validLogin(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionLogin permissionMenu = handlerMethod.getMethodAnnotation(PermissionLogin.class);
        if (permissionMenu != null) {
            // 判断是否登录
            if (!isLogin(response)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 写响应
     *
     * @param response
     * @param resp
     */
    public static void writeResponse(HttpServletResponse response, Response resp) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(resp));
            writer.flush();
        } catch (Exception e) {
            log.error("写响应异常", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
