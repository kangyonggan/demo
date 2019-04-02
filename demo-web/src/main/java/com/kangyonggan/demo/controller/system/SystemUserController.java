package com.kangyonggan.demo.controller.system;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.model.User;
import com.kangyonggan.demo.service.system.RoleService;
import com.kangyonggan.demo.service.system.UserService;
import com.kangyonggan.demo.util.Collections3;
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

    @Autowired
    private RoleService roleService;

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
     * 删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("{userId:[\\d]+}")
    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_USER")
    public Response delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
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
     * 获取角色
     *
     * @param userId
     * @return
     */
    @GetMapping("{userId:[\\d]+}/role")
    @ApiOperation("获取角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_USER")
    public Response role(@PathVariable Long userId) {
        Response response = successResponse();
        List<Role> allRoles = roleService.findAllRoles();
        List<String> userRoles = Collections3.extractToList(roleService.findRolesByUserId(userId), "roleId");

        response.put("allRoles", allRoles);
        response.put("userRoles", userRoles);
        return response;
    }

    /**
     * 更新用户
     *
     * @param userId
     * @param user
     * @param roleIds
     * @return
     */
    @PutMapping("{userId:[\\d]+}")
    @ApiOperation("更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, example = "1"),
            @ApiImplicitParam(name = "roleIds", value = "角色ID", required = false, example = "[\"1\"]")
    })
    @PermissionMenu("SYSTEM_USER")
    public Response update(@PathVariable Long userId, User user, @RequestParam(value = "roleIds", required = false) String[] roleIds) {
        user.setUserId(userId);
        userService.updateUser(user, roleIds);
        return successResponse();
    }

}
