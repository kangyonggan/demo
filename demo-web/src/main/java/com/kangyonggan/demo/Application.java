package com.kangyonggan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author kangyonggan
 * @since 7/31/18
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.kangyonggan.demo.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
