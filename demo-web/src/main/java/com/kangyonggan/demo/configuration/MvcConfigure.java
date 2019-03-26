package com.kangyonggan.demo.configuration;

import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Configuration
public class MvcConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 处理请求
        registry.addInterceptor(new ParamsInterceptor()).addPathPatterns("/**");
    }
}
