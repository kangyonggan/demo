package com.kangyonggan.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.demo.annotation.CacheDel;
import com.kangyonggan.demo.annotation.MethodLog;
import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.dto.Params;
import com.kangyonggan.demo.dto.Query;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import com.kangyonggan.demo.util.Digests;
import com.kangyonggan.demo.util.Encodes;
import com.kangyonggan.demo.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<User> searchUsers(Params params) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        Query query = params.getQuery();

        String email = query.getString("email");
        if (StringUtils.isNotEmpty(email)) {
            criteria.andEqualTo("email", email);
        }
        Date startDate = query.getDate("startDate");
        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }
        Date endDate = query.getDate("endDate");
        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("user_id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    @MethodLog
    @CacheDel("demo:user::email:*")
    public void updateUser(User user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            entryptPassword(user);
        }
        myMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean existsEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return super.exists(user);
    }

    @Override
    @MethodLog
    public void saveUser(User user) {
        entryptPassword(user);
        myMapper.insertSelective(user);
    }

    @Override
    @MethodLog
    @CacheDel("demo:user::email:*")
    public void deleteUser(Long userId) {
        myMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过N次 sha-1 hash
     *
     * @param user
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(AppConstants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

}
