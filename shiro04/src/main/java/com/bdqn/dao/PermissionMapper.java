package com.bdqn.dao;

import java.util.Set;

public interface PermissionMapper {
    /**
     * 根据用户id查询该用户拥有的权限
     * @param userId
     * @return
     * @throws Exception
     */
    Set<String> findPermissionListByUserId(int userId) throws Exception;
}
