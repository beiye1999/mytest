package com.bdqn.entity;

/**
 * 权限类
 */
public class Permission {
    private Integer permissionid;//权限编号
    private String permissionname;//权限名称
    private String permissioncode;//权限编码

    public Integer getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(Integer permissionid) {
        this.permissionid = permissionid;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    public String getPermissioncode() {
        return permissioncode;
    }

    public void setPermissioncode(String permissioncode) {
        this.permissioncode = permissioncode;
    }
}