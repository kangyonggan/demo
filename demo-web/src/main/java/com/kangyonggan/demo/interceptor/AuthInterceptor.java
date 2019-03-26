package com.kangyonggan.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.annotation.PermissionRole;
import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.constants.Resp;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.MenuService;
import com.kangyonggan.demo.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            HttpSession session = request.getSession();

            // 校验登录权限注解
            if (!validLogin(session, response, handlerMethod)) {
                return false;
            }

            // 校验菜单权限注解
            if (!validMenu(session, response, handlerMethod)) {
                return false;
            }

            // 校验角色权限注解
            if (!validRole(session, response, handlerMethod)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 校验角色权限注解
     *
     * @param session
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validRole(HttpSession session, HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionRole permissionRole = handlerMethod.getMethodAnnotation(PermissionRole.class);
        if (permissionRole != null) {
            if (!isLogin(session, response)) {
                return false;
            }

            User user = (User) session.getAttribute(AppConstants.KEY_SESSION_USER);
            boolean hasRole = roleService.hasRoles(user.getUserId(), permissionRole.value());
            if (!hasRole) {
                // 9997: 权限不足
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验菜单权限注解
     *
     * @param session
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validMenu(HttpSession session, HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionMenu permissionMenu = handlerMethod.getMethodAnnotation(PermissionMenu.class);
        if (permissionMenu != null) {
            if (!isLogin(session, response)) {
                return false;
            }

            User user = (User) session.getAttribute(AppConstants.KEY_SESSION_USER);
            boolean hasMenu = menuService.hasMenus(user.getUserId(), permissionMenu.value());
            if (!hasMenu) {
                // 9997: 权限不足
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否登录，没登录就重定向到登录
     *
     * @param session
     * @param response
     * @return
     */
    private boolean isLogin(HttpSession session, HttpServletResponse response) {
        // 判断是否登录
        if (session.getAttribute(AppConstants.KEY_SESSION_USER) == null) {
            // 9998: 登录失效
            Response resp = Response.getFailureResponse(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
            writeResponse(response, resp);
            return false;
        }
        return true;
    }

    /**
     * 校验登录权限注解
     *
     * @param session
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validLogin(HttpSession session, HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionLogin permissionMenu = handlerMethod.getMethodAnnotation(PermissionLogin.class);
        if (permissionMenu != null) {
            // 判断是否登录
            if (!isLogin(session, response)) {
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
