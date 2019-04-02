package com.kangyonggan.demo.controller.user;

import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import com.kangyonggan.demo.model.UserProfile;
import com.kangyonggan.demo.service.system.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
@RestController
@RequestMapping("user/profile")
@Api(tags = "UserProfileController", description = "用户信息接口")
public class UserProfileController extends BaseController {

    @Autowired
    private UserProfileService userProfileService;

    /**
     * 更新用户信息
     *
     * @param userProfile
     * @return
     */
    @PutMapping
    @ApiOperation("更新用户信息")
    @PermissionMenu("USER_PROFILE")
    public Response update(UserProfile userProfile) {
        userProfile.setUserId(ParamsInterceptor.getUser().getUserId());
        userProfileService.updateUserProfile(userProfile);
        return successResponse();
    }

}
