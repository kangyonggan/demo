package com.kangyonggan.demo.controller.user;

import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.constants.AppConstants;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.interceptor.ParamsInterceptor;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.model.UserProfile;
import com.kangyonggan.demo.service.system.UserProfileService;
import com.kangyonggan.demo.service.system.UserService;
import com.kangyonggan.demo.util.Digests;
import com.kangyonggan.demo.util.Encodes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private UserService userService;

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
    public Response updateProfile(UserProfile userProfile) {
        userProfile.setUserId(ParamsInterceptor.getUser().getUserId());
        userProfileService.updateUserProfile(userProfile);
        return successResponse();
    }

    /**
     * 更改密码
     *
     * @param oldPassword
     * @param password
     * @return
     */
    @PutMapping("updatePassword")
    @ApiOperation("更改密码")
    @PermissionMenu("USER_PROFILE")
    public Response updatePassword(@RequestParam String oldPassword, @RequestParam String password) {
        Response response = successResponse();
        User user = userService.findUserByEmail(ParamsInterceptor.getUser().getEmail());

        byte[] salt = Encodes.decodeHex(user.getSalt());
        byte[] hashPassword = Digests.sha1(oldPassword.getBytes(), salt, AppConstants.HASH_INTERATIONS);
        String targetPassword = Encodes.encodeHex(hashPassword);
        if (!user.getPassword().equals(targetPassword)) {
            return response.failure("老密码错误");
        }

        user.setPassword(password);
        userService.updateUser(user, null);
        return successResponse();
    }

}
