package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return myMapper.selectOne(user);
    }
}
