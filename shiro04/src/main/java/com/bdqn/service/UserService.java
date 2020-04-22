package com.bdqn.service;

import com.bdqn.entity.User;

public interface UserService {
    /**
     * 根据用户查询用户信息
     * @param username
     * @return
     * @throws Exception
     */
    User findUserByUserName(String username) throws Exception;
}
