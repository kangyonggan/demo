package com.kangyonggan.demo.controller.system;

import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.Menu;
import com.kangyonggan.demo.service.MenuService;
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
@RequestMapping("system/menu")
@Api(tags = "SystemMenuController", description = "菜单相关接口")
public class SystemMenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表
     *
     * @return
     */
    @GetMapping
    @ApiOperation("查询菜单列表")
    @PermissionMenu("SYSTEM_MENU")
    public Response index() {
        Response response = successResponse();
        List<Menu> menus = menuService.findAllMenus();

        response.put("menus", menus);
        return response;
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @DeleteMapping("{menuId:[\\d]+}")
    @ApiOperation("删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_MENU")
    public Response delete(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return successResponse();
    }

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    @PostMapping
    @ApiOperation("保存菜单")
    @PermissionMenu("SYSTEM_MENU")
    public Response save(Menu menu) {
        menuService.saveMenu(menu);
        return successResponse();
    }

    /**
     * 更新菜单
     *
     * @param menuId
     * @param menu
     * @return
     */
    @PutMapping("{menuId:[\\d]+}")
    @ApiOperation("更新菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_MENU")
    public Response update(@PathVariable Long menuId, Menu menu) {
        menu.setMenuId(menuId);
        menuService.updateMenu(menu);
        return successResponse();
    }

}
