package com.kangyonggan.demo.controller.system;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.demo.annotation.PermissionMenu;
import com.kangyonggan.demo.controller.BaseController;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.model.Dict;
import com.kangyonggan.demo.service.system.DictService;
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
@RequestMapping("system/dict")
@Api(tags = "SystemDictController", description = "字典相关接口")
public class SystemDictController extends BaseController {

    @Autowired
    private DictService dictService;

    /**
     * 查询字典列表
     *
     * @return
     */
    @GetMapping
    @ApiOperation("查询字典列表")
    @PermissionMenu("SYSTEM_DICT")
    public Response index() {
        Response response = successResponse();
        List<Dict> dicts = dictService.searchDicts(getRequestParams());
        PageInfo<Dict> pageInfo = new PageInfo<>(dicts);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 删除字典
     *
     * @param dictId
     * @return
     */
    @DeleteMapping("{dictId:[\\d]+}")
    @ApiOperation("删除字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictId", value = "字典ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_DICT")
    public Response delete(@PathVariable Long dictId) {
        dictService.deleteDict(dictId);
        return successResponse();
    }

    /**
     * 保存字典
     *
     * @param dict
     * @return
     */
    @PostMapping
    @ApiOperation("保存字典")
    @PermissionMenu("SYSTEM_DICT")
    public Response save(Dict dict) {
        dictService.saveDict(dict);
        return successResponse();
    }

    /**
     * 更新字典
     *
     * @param dictId
     * @param dict
     * @return
     */
    @PutMapping("{dictId:[\\d]+}")
    @ApiOperation("更新字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictId", value = "字典ID", required = true, example = "1")
    })
    @PermissionMenu("SYSTEM_DICT")
    public Response update(@PathVariable Long dictId, Dict dict) {
        dict.setDictId(dictId);
        dictService.updateDict(dict);
        return successResponse();
    }

}
