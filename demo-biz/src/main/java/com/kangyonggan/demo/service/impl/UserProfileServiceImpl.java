package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.model.UserProfile;
import com.kangyonggan.demo.service.UserProfileService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author kangyonggan
 * @since 2018/12/9 0009
 */
@Service
public class UserProfileServiceImpl extends BaseService<UserProfile> implements UserProfileService {

    @Override
    public void updateUserProfile(UserProfile userProfile) {
        Example example = new Example(UserProfile.class);
        example.createCriteria().andEqualTo("userId", userProfile.getUserId());

        myMapper.updateByExampleSelective(userProfile, example);
    }

    @Override
    public void saveUserProfile(UserProfile userProfile) {
        myMapper.insertSelective(userProfile);
    }
}
