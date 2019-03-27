package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.annotation.CacheDel;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
@Service
@CacheConfig(cacheNames = "demo:user")
public class UserServiceImpl extends BaseService<User> implements UserService {

    /**
     * 如果有缓存，则直接返回缓存，否则执行方法体，并缓存方法结果，其中key="user::email:admin@kangyonggan.com"
     *
     * @param email
     * @return
     */
    @Override
    @Cacheable(key = "'email:' + #email")
    public User findUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return myMapper.selectOne(user);
    }

    /**
     * 如果有缓存，则直接返回缓存，否则执行方法体，并缓存方法结果，其中key="user::all"
     *
     * @return
     */
    @Override
    @Cacheable(key = "'all'")
    public List<User> findAllUsers() {
        return myMapper.selectAll();
    }

    /**
     * 清除缓存。key="user::all"
     *
     * @param user
     */
    @Override
    @CacheEvict(key = "'all'")
    public void saveUser(User user) {
        myMapper.insertSelective(user);
    }

    /**
     * 清除缓存。key="user::all"
     * 清除缓存。key="user::email:*"
     *
     * @param user
     */
    @Override
    @CacheEvict(key = "'all'")
    @CacheDel("demo:user::email:*")
    public void updateUser(User user) {
        myMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 清除缓存。key="user::all"
     * 清除缓存。key="user::email:*"
     *
     * @param userId
     */
    @Override
    @CacheEvict(key = "'all'")
    @CacheDel("demo:user::email:*")
    public void deleteUser(Long userId) {
        myMapper.deleteByPrimaryKey(userId);
    }
}
