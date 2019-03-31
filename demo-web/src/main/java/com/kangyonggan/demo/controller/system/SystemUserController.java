package com.kangyonggan.demo.controller.system;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019/3/30 0030
 */
@RestController
@RequestMapping("system/user")
@Api(tags = "SystemUserController", description = "用户相关接口")
public class SystemUserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     *
     * @return
     */
    @GetMapping
    @ApiOperation("查询用户列表")
    @PermissionMenu("SYSTEM_USER")
    public Response index() {
        Response response = successResponse();
        List<User> users = userService.searchUsers(getRequestParams());
        PageInfo<User> pageInfo = new PageInfo<>(users);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 删除/恢复用户
     *
     * @return
     */
    @GetMapping("{userId:[\\d]+}/delete/{isDeleted:\\b0\\b|\\b1\\b}")
    @ApiOperation("删除/恢复用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, example = "1"),
            @ApiImplicitParam(name = "isDeleted", value = "是否删除（1：删除，0:恢复）", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_USER")
    public Response delete(@PathVariable Long userId, @PathVariable byte isDeleted) {
        User user = new User();
        user.setUserId(userId);
        user.setIsDeleted(isDeleted);

        userService.updateUser(user);
        return successResponse();
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation("保存用户")
    @PermissionMenu("SYSTEM_USER")
    public Response save(User user) {
        userService.saveUser(user);
        return successResponse();
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @PutMapping
    @ApiOperation("更新用户")
    @PermissionMenu("SYSTEM_USER")
    public Response update(User user) {
        userService.updateUser(user);
        return successResponse();
    }

}
