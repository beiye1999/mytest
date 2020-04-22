package com.bdqn.service.impl;

import com.bdqn.dao.UserMapper;
import com.bdqn.entity.User;
import com.bdqn.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper  userMapper;

    @Override
    public User findUserByUserName(String username) throws Exception {
        return userMapper.findUserByUserName(username);
    }
}
