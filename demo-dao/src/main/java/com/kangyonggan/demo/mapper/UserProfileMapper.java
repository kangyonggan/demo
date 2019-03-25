package com.kangyonggan.demo.mapper;

import com.kangyonggan.demo.model.UserProfile;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kangyonggan
 * @since 8/8/18
 */
@Mapper
public interface UserProfileMapper extends MyMapper<UserProfile> {
}