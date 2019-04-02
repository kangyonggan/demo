package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.constants.EnumUtil;
import com.kangyonggan.demo.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
@RestController
@RequestMapping("enum")
@Api(tags = "EnumController", description = "枚举相关接口")
public class EnumController extends BaseController {

    /**
     * 获取枚举map
     *
     * @param enumKey
     * @return
     */
    @GetMapping("{enumKey}/map")
    @PermissionLogin
    @ApiOperation("获取枚举map")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enumKey", value = "枚举的key", required = true, example = "DictType")
    })
    public Response map(@PathVariable String enumKey) {
        Response response = successResponse();

        response.put("enumMap", EnumUtil.getInstance().getEnumMap(enumKey));
        return response;
    }

    /**
     * 获取枚举list
     *
     * @param enumKey
     * @return
     */
    @GetMapping("{enumKey}/list")
    @PermissionLogin
    @ApiOperation("获取枚举list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enumKey", value = "枚举的key", required = true, example = "DictType")
    })
    public Response list(@PathVariable String enumKey) {
        Response response = successResponse();

        response.put("enumList", EnumUtil.getInstance().getEnumList(enumKey));
        return response;
    }

}
