package com.kangyonggan.demo.dto;

import com.kangyonggan.demo.model.UserProfile;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
@Data
public class UserDto extends UserProfile {

    /**
     * 电子邮箱
     */
    private String email;

}
