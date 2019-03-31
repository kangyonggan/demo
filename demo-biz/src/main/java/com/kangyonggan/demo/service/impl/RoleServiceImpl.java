package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.constants.YesNo;
import com.kangyonggan.demo.mapper.RoleMapper;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
