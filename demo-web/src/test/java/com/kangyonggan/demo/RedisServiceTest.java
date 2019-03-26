package com.kangyonggan.demo;

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

    @Test
    public void test() {
        logger.info(redisService.get("demo:test"));
        redisService.set("demo:test", 1, 10);
        logger.info(redisService.get("demo:test"));
    }

}
