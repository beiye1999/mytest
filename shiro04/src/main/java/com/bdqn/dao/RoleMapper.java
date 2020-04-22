package com.bdqn.dao;

import java.util.Set;

public interface RoleMapper {
    /**
     * 根据用户id查询该用户拥有的角色列表
     * @param userId    用户编号
     * @return
     * @throws Exception
     */
    Set<String> findRoleListByUserId(int userId) throws Exception;
}
