package com.kangyonggan.demo.configuration;

import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.interceptor.AuthInterceptor;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import com.kangyonggan.demo.service.MenuService;
import com.kangyonggan.demo.service.RoleService;
import com.kangyonggan.demo.util.SpringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    /**
     * 允许跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS").exposedHeaders(AppConstants.HEADER_TOKEN_NAME);
    }

    /**
     * 添加自定义拦截器
     *
     * @param registry
     */
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
