package com.kangyonggan.demo;

import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import com.kangyonggan.demo.service.impl.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

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

    @Test
    public void findAllUsers() {
        List<User> users = userService.findAllUsers();
        logger.info(users);
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setEmail("test@kangyonnggan.com");
        user.setPassword("xxx");
        user.setSalt("xxx");

        userService.saveUser(user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setUserId(1L);
        user.setCreatedTime(new Date());

        userService.updateUser(user);
    }

    @Test
    public void deleteUser() {
        userService.deleteUser(2L);
    }

}
