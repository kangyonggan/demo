package com.kangyonggan.demo.service.impl.system;

import com.kangyonggan.demo.annotation.CacheDel;
import com.kangyonggan.demo.annotation.MethodLog;
import com.kangyonggan.demo.constants.YesNo;
import com.kangyonggan.demo.model.Dict;
import com.kangyonggan.demo.service.BaseService;
import com.kangyonggan.demo.service.system.DictService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
@Service
@CacheConfig(cacheNames = "demo:dict")
public class DictServiceImpl extends BaseService<Dict> implements DictService {

    @Override
    @MethodLog
    @CacheDel("demo:dict:*")
    public void deleteDict(Long dictId) {
        myMapper.deleteByPrimaryKey(dictId);
    }

    @Override
    @MethodLog
    @CacheEvict(key = "'type:' + #dict.dictType")
    public void saveDict(Dict dict) {
        myMapper.insertSelective(dict);
    }

    @Override
    @MethodLog
    @CacheDel("demo:dict:*")
    public void updateDict(Dict dict) {
        myMapper.updateByPrimaryKeySelective(dict);
    }

    @Override
    public boolean existsDictCode(String dictType, String dictCode) {
        Dict dict = new Dict();
        dict.setDictType(dictType);
        dict.setDictCode(dictCode);

        return super.exists(dict);
    }

    @Override
    @MethodLog
    @Cacheable(key = "'type:' + #dictType")
    public List<Dict> findDictsByDictType(String dictType) {
        Example example = new Example(Dict.class);
        example.createCriteria().andEqualTo("isDeleted", YesNo.NO.getCode()).andEqualTo("dictType", dictType);

        example.selectProperties("dictCode", "value");

        example.setOrderByClause("sort asc");
        return myMapper.selectByExample(example);
    }

    @Override
    @MethodLog
    @Cacheable(key = "'type:' + #dictType + ':code:' + #dictCode")
    public Dict findDictByTypeAndCode(String dictType, String dictCode) {
        Dict dict = new Dict();
        dict.setDictType(dictType);
        dict.setDictCode(dictCode);
        dict.setIsDeleted(YesNo.NO.getCode());

        return myMapper.selectOne(dict);
    }
}
