package com.kangyonggan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author kangyonggan
 * @since 7/31/18
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableRedisHttpSession
@MapperScan("com.kangyonggan.demo.mapper")
@PropertySource(value = "classpath:app-${spring.profiles.active}.properties", encoding = "UTF-8")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
