package com.kangyonggan.demo.service.system;

import com.kangyonggan.demo.model.UserProfile;

/**
 * @author kangyonggan
 * @since 2018/12/9 0009
 */
public interface UserProfileService {

    /**
     * 更新用户信息
     *
     * @param userProfile
     */
    void updateUserProfile(UserProfile userProfile);

    /**
     * 保存用户信息
     *
     * @param userProfile
     */
    void saveUserProfile(UserProfile userProfile);

}
