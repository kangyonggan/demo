package com.kangyonggan.demo.configuration;

import com.kangyonggan.demo.interceptor.AuthInterceptor;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import com.kangyonggan.demo.service.MenuService;
import com.kangyonggan.demo.service.RoleService;
import com.kangyonggan.demo.util.SpringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        RoleService roleService = SpringUtils.getBean(RoleService.class);
        MenuService menuService = SpringUtils.getBean(MenuService.class);

        // 处理请求
        registry.addInterceptor(new ParamsInterceptor()).addPathPatterns("/**");

        // 权限认证
        registry.addInterceptor(new AuthInterceptor(roleService, menuService)).addPathPatterns("/**");
    }
}
