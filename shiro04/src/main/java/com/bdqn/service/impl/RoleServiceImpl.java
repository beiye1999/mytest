package com.bdqn.service.impl;

import com.bdqn.dao.RoleMapper;
import com.bdqn.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Set<String> findRoleListByUserId(int userId) throws Exception {
        return roleMapper.findRoleListByUserId(userId);
    }
}
