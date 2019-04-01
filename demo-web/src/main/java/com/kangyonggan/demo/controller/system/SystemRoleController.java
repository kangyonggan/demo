package com.kangyonggan.demo.controller.system;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.service.RoleService;
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
@RequestMapping("system/role")
@Api(tags = "SystemRoleController", description = "角色相关接口")
public class SystemRoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     *
     * @return
     */
    @GetMapping
    @ApiOperation("查询角色列表")
    @PermissionMenu("SYSTEM_ROLE")
    public Response index() {
        Response response = successResponse();
        List<Role> roles = roleService.searchRoles(getRequestParams());
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("{roleId:[\\d]+}")
    @ApiOperation("删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_ROLE")
    public Response delete(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return successResponse();
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @PostMapping
    @ApiOperation("保存角色")
    @PermissionMenu("SYSTEM_ROLE")
    public Response save(Role role) {
        roleService.saveRole(role);
        return successResponse();
    }

    /**
     * 更新角色
     *
     * @param roleId
     * @param role
     * @return
     */
    @PutMapping("{roleId:[\\d]+}")
    @ApiOperation("更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_ROLE")
    public Response update(@PathVariable Long roleId, Role role) {
        role.setRoleId(roleId);
        roleService.updateRole(role);
        return successResponse();
    }

}
