package com.kangyonggan.demo.mapper;

import com.kangyonggan.demo.model.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.provider.SqlServerProvider;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 8/8/18
 */
@Mapper
public interface UserMapper extends MyMapper<User> {

    /**
     * 重新指定主键
     *
     * @param user
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @InsertProvider(type = SqlServerProvider.class, method = "dynamicSQL")
    @Override
    int insertSelective(User user);

    /**
     * 批量插入授权角色
     *
     * @param userId
     * @param roleIds
     */
    void insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<String> roleIds);

    /**
     * 查看授权用户
     *
     * @param roleId
     * @return
     */
    List<User> selectUsersByRoleId(Long roleId);
}