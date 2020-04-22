package com.bdqn.vo;

import com.bdqn.entity.User;

import java.util.Set;

public class LoginUserVo {

    private User user;//当前登录用户信息
    private Set<String> roles;//当前登录用户拥有的角色列表
    private Set<String> permissions;//当前登录用户拥有角色对应的权限列表

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public LoginUserVo() {
    }

    /**
     * 创建登录用户信息
     * @param user          登录用户对象
     * @param roles         角色列表
     * @param permissions   权限列表
     */
    public LoginUserVo(User user, Set<String> roles, Set<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }
}
