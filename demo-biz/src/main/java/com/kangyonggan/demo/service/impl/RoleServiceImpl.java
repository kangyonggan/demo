package com.kangyonggan.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.demo.annotation.MethodLog;
import com.kangyonggan.demo.constants.YesNo;
import com.kangyonggan.demo.dto.Params;
import com.kangyonggan.demo.dto.Query;
import com.kangyonggan.demo.mapper.RoleMapper;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.service.RoleService;
import com.kangyonggan.demo.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 12/6/18
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean hasRoles(Long userId, String... roleCodes) {
        return roleMapper.selectExistsUserRoleCode(userId, roleCodes);
    }

    @Override
    public void deleteAllRolesByUserId(Long userId) {
        roleMapper.deleteAllRolesByUserId(userId);
    }

    @Override
    public List<Role> findAllRoles() {
        Role role = new Role();
        role.setIsDeleted(YesNo.NO.getCode());

        return myMapper.select(role);
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<Role> searchRoles(Params params) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        Query query = params.getQuery();

        String roleCode = query.getString("roleCode");
        if (StringUtils.isNotEmpty(roleCode)) {
            criteria.andEqualTo("roleCode", roleCode);
        }
        String roleName = query.getString("roleName");
        if (StringUtils.isNotEmpty(roleName)) {
            criteria.andEqualTo("roleName", roleName);
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
            example.setOrderByClause("role_id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    @MethodLog
    public void deleteRole(Long roleId) {
        myMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    @MethodLog
    public void saveRole(Role role) {
        myMapper.insertSelective(role);
    }

    @Override
    @MethodLog
    public void updateRole(Role role) {
        myMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public boolean existsRoleCode(String roleCode) {
        Role role = new Role();
        role.setRoleCode(roleCode);
        return super.exists(role);
    }

}
