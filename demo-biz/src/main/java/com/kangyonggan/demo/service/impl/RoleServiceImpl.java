package com.kangyonggan.demo.service.impl;

import com.github.pagehelper.PageHelper;
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
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
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
    public boolean hasRole(Long userId, String roleCode) {
        return roleMapper.selectExistsUserRoleCode(userId, roleCode);
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<Role> findAllRoles() {
        Role role = new Role();
        role.setIsDeleted(YesNo.NO.getCode());

        return myMapper.select(role);
    }

    @Override
    public void deleteAllRolesByUserId(Long userId) {
        roleMapper.deleteAllRolesByUserId(userId);
    }

    @Override
    public List<Role> searchRoles(Params params) {
        Example example = new Example(Role.class);
        Query query = params.getQuery();

        Example.Criteria criteria = example.createCriteria();
        String roleCode = query.getString("roleCode");
        if (StringUtils.isNotEmpty(roleCode)) {
            criteria.andEqualTo("roleCode", roleCode);
        }
        String roleName = query.getString("roleName");
        if (StringUtils.isNotEmpty(roleName)) {
            criteria.andEqualTo("roleName", roleName);
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
    public void saveRole(Role role) {
        myMapper.insertSelective(role);
    }

    @Override
    public Role findRoleByRoleId(Long roleId) {
        return myMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public void updateRole(Role role) {
        if (role.getRoleId() == null) {
            return;
        }
        myMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRoles(String roleIds) {
        if (StringUtils.isEmpty(roleIds)) {
            return;
        }
        String[] ids = roleIds.split(",");
        Example example = new Example(Role.class);
        example.createCriteria().andIn("roleId", Arrays.asList(ids));

        Role role = new Role();
        role.setIsDeleted(YesNo.YES.getCode());

        myMapper.updateByExampleSelective(role, example);
    }

    @Override
    public void restoreRoles(String roleIds) {
        if (StringUtils.isEmpty(roleIds)) {
            return;
        }
        String[] ids = roleIds.split(",");
        Example example = new Example(Role.class);
        example.createCriteria().andIn("roleId", Arrays.asList(ids));

        Role role = new Role();
        role.setIsDeleted(YesNo.NO.getCode());

        myMapper.updateByExampleSelective(role, example);
    }

    @Override
    public boolean existsRoleCode(String roleCode) {
        Role role = new Role();
        role.setRoleCode(roleCode);

        return super.exists(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenus(Long roleId, String menuIds) {
        deleteRoleMenus(roleId);

        if (StringUtils.isNotEmpty(menuIds)) {
            roleMapper.insertRoleMenus(roleId, Arrays.asList(menuIds.split(",")));
        }
    }


    /**
     * 删除角色菜单
     *
     * @param roleId
     */
    private void deleteRoleMenus(Long roleId) {
        roleMapper.deleteRoleMenus(roleId);
    }

}
