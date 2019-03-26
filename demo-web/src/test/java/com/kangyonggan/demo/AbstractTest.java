package com.kangyonggan.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author kangyonggan
 * @since 7/19/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableRedisHttpSession(redisNamespace = "demo:session")
@MapperScan("com.kangyonggan.demo.mapper")
@PropertySource(value = "classpath:app-dev.properties", encoding = "UTF-8")
public class AbstractTest {

    protected Logger logger = LogManager.getLogger(AbstractTest.class);

}
