package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
@Service
@CacheConfig(cacheNames = "demo:user")
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Override
    @Cacheable(key = "'email:' + #email")
    public User findUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return myMapper.selectOne(user);
    }

}
