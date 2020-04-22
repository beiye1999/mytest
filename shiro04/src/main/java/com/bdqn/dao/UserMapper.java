package com.bdqn.dao;

import com.bdqn.entity.User;

public interface UserMapper {

    /**
     * 根据用户查询用户信息
     * @param username
     * @return
     * @throws Exception
     */
    User findUserByUserName(String username) throws Exception;

}
