package com.bdqn.realm;

import com.bdqn.entity.User;
import com.bdqn.service.PermissionService;
import com.bdqn.service.RoleService;
import com.bdqn.service.UserService;
import com.bdqn.vo.LoginUserVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 身份验证：为当前登录的用户进行身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try {
            //得到当前登录用户的信息
            String username = token.getPrincipal().toString();
            //调用根据用户名查询用户信息的方法
            User user =  userService.findUserByUserName(username);
            //判断用户对象是否为空
            if(user!=null){//用户信息是存在的
                //查询当前登录用户拥有的角色信息
                Set<String> roles = roleService.findRoleListByUserId(user.getUserid());
                //查询当前登录用户拥有哪些权限
                Set<String> permissions = permissionService.findPermissionListByUserId(user.getUserid());
                //创建登录用户对象，传入用户信息，角色列表，权限列表
                LoginUserVo loginUserVo = new LoginUserVo(user,roles,permissions);
                //创建盐值(以用户名作为盐值)
                ByteSource salt = ByteSource.Util.bytes(user.getUsername());
                //创建身份验证信息对象
                //参数1：用户名  参数2：密码  参数3：盐值  参数4：域名（自定义）
                SimpleAuthenticationInfo simpleAuthenticationInfo =
                        new SimpleAuthenticationInfo(loginUserVo,user.getUserpwd(),salt,getName());
                //返回验证信息
                return simpleAuthenticationInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//登录失败、用户名不存在
    }

    /**
     * 授权：为当前登录的用户授权相应的权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //得到当前登录用户对象
        LoginUserVo loginUserVo = (LoginUserVo) principals.getPrimaryPrincipal();
        //查询当前登录用户拥有哪些角色
        Set<String> roles = loginUserVo.getRoles();
        //查询当前登录用户拥有哪些权限
        Set<String> permissions = loginUserVo.getPermissions();

        //创建授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //判断角色列表是否为空
        if(roles!=null && roles.size()>0){
            info.setRoles(roles);//设置角色列表
        }
        //判断权限列表是否为空
        if(permissions!=null && permissions.size()>0){
            info.setStringPermissions(permissions);//设置角色列表
        }
        return info;
    }


}
