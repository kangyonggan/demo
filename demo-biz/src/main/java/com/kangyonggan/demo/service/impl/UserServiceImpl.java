package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.constants.YesNo;
import com.kangyonggan.demo.mapper.UserMapper;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.RoleService;
import com.kangyonggan.demo.service.UserService;
import com.kangyonggan.demo.util.Digests;
import com.kangyonggan.demo.util.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return myMapper.selectOne(user);
    }

    @Override
    public boolean existsEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return super.exists(user);
    }

    @Override
    public User findUserByUserId(Long userId) {
        return myMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void deleteUsers(String userIds) {
        if (StringUtils.isEmpty(userIds)) {
            return;
        }
        String[] ids = userIds.split(",");
        Example example = new Example(User.class);
        example.createCriteria().andIn("userId", Arrays.asList(ids));

        User user = new User();
        user.setIsDeleted(YesNo.YES.getCode());

        myMapper.updateByExampleSelective(user, example);
    }

    @Override
    public void restoreUsers(String userIds) {
        if (StringUtils.isEmpty(userIds)) {
            return;
        }
        String[] ids = userIds.split(",");
        Example example = new Example(User.class);
        example.createCriteria().andIn("userId", Arrays.asList(ids));

        User user = new User();
        user.setIsDeleted(YesNo.NO.getCode());

        myMapper.updateByExampleSelective(user, example);
    }

    @Override
    public void updateUserPassword(User user) {
        if (user.getUserId() == null) {
            return;
        }
        User u = new User();
        u.setPassword(user.getPassword());
        u.setUserId(user.getUserId());
        entryptPassword(u);

        myMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoles(Long userId, String roleIds) {
        roleService.deleteAllRolesByUserId(userId);

        if (StringUtils.isNotEmpty(roleIds)) {
            saveUserRoles(userId, roleIds);
        }
    }

    @Override
    public List<User> findUsersByRoleId(Long roleId) {
        return userMapper.selectUsersByRoleId(roleId);
    }

    /**
     * 批量保存授权角色
     *
     * @param userId
     * @param roleIds
     */
    private void saveUserRoles(Long userId, String roleIds) {
        userMapper.insertUserRoles(userId, Arrays.asList(roleIds.split(",")));
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
