package com.kangyonggan.demo;

import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.system.UserService;
import com.kangyonggan.demo.service.impl.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kangyonggan
 * @since 2019-03-26
 */
public class RedisServiceTest extends AbstractTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        logger.info(redisService.get("demo:test"));
        redisService.set("demo:test", 1, 10);
        logger.info(redisService.get("demo:test"));
    }

    @Test
    public void findUserByEmail() {
        User user = userService.findUserByEmail("admin@kangyonggan.com");
        logger.info(user);
    }
}
