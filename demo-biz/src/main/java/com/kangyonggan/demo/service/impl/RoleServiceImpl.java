package com.kangyonggan.demo.service.impl;

import com.kangyonggan.demo.mapper.RoleMapper;
import com.kangyonggan.demo.model.Role;
import com.kangyonggan.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
