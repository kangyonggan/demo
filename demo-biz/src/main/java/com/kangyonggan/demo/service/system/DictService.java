package com.kangyonggan.demo.service.system;

import com.kangyonggan.demo.dto.Params;
import com.kangyonggan.demo.model.Dict;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
public interface DictService {

    /**
     * 搜索字典
     *
     * @param params
     * @return
     */
    List<Dict> searchDicts(Params params);

    /**
     * 删除字典
     *
     * @param dictId
     */
    void deleteDict(Long dictId);

    /**
     * 保存字典
     *
     * @param dict
     */
    void saveDict(Dict dict);

    /**
     * 更新字典
     *
     * @param dict
     */
    void updateDict(Dict dict);

    /**
     * 校验字典代码是否存在
     *
     * @param dictType
     * @param dictCode
     * @return
     */
    boolean existsDictCode(String dictType, String dictCode);
}
