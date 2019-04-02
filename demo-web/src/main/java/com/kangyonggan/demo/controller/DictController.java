package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.service.system.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
@RestController
@RequestMapping("dict")
@Api(tags = "DictController", description = "字典相关接口")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    /**
     * 获取字典列表
     *
     * @param dictType
     * @return
     */
    @GetMapping("{dictType}")
    @PermissionLogin
    @ApiOperation("获取字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典类型", required = true, example = "ID_TYPE")
    })
    public Response list(@PathVariable String dictType) {
        Response response = successResponse();

        response.put("dicts", dictService.findDictsByDictType(dictType));
        return response;
    }

}
