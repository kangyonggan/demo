package com.kangyonggan.demo.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.demo.annotation.CacheDel;
import com.kangyonggan.demo.annotation.MethodLog;
import com.kangyonggan.demo.constants.YesNo;
import com.kangyonggan.demo.dto.Params;
import com.kangyonggan.demo.dto.Query;
import com.kangyonggan.demo.model.Dict;
import com.kangyonggan.demo.service.BaseService;
import com.kangyonggan.demo.service.system.DictService;
import com.kangyonggan.demo.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-04-02
 */
@Service
@CacheConfig(cacheNames = "demo:dict")
public class DictServiceImpl extends BaseService<Dict> implements DictService {

    @Override
    public List<Dict> searchDicts(Params params) {
        Example example = new Example(Dict.class);
        Example.Criteria criteria = example.createCriteria();
        Query query = params.getQuery();

        String dictType = query.getString("dictType");
        if (StringUtils.isNotEmpty(dictType)) {
            criteria.andEqualTo("dictType", dictType);
        }
        String dictCode = query.getString("dictCode");
        if (StringUtils.isNotEmpty(dictCode)) {
            criteria.andEqualTo("dictCode", dictCode);
        }
        Date startDate = query.getDate("startDate");
        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }
        Date endDate = query.getDate("endDate");
        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("dict_id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

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
