package com.bdqn.service.impl;

import com.bdqn.dao.PermissionMapper;
import com.bdqn.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;


@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> findPermissionListByUserId(int userId) throws Exception {
        return permissionMapper.findPermissionListByUserId(userId);
    }
}
