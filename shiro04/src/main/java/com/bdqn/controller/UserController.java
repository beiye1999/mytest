package com.bdqn.controller;

import com.bdqn.entity.User;
import com.bdqn.vo.LoginUserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 去到登录页面
     * @return
     */
    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(User user, HttpSession session){
        //得到当前登录主体
        Subject subject = SecurityUtils.getSubject();
        //创建口令
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getUserpwd());
        try {
            //登录
            subject.login(token);
            //创建登录对象
            LoginUserVo loginUserVo = (LoginUserVo) subject.getPrincipal();
            //保存会话
            session.setAttribute("loginUser",loginUserVo.getUser());
            //跳转到后台主页
            return "main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        //登录失败
        return "redirect:/user/tologin";
    }



    @ResponseBody
    @RequiresPermissions("user:add")
    @RequestMapping("/userAdd")
    public String userAdd(){
        return "userAdd";
    }


    @ResponseBody
    @RequiresPermissions("user:update")
    @RequestMapping("/userUpdate")
    public String userUpdate(){
        return "userUpdate";
    }


    @ResponseBody
    @RequestMapping("/userDelete")
    @RequiresPermissions("user:delete")
    public String userDelete(){
        return "userDelete";
    }


    @ResponseBody
    @RequestMapping("/userQuery")
    @RequiresPermissions("user:query")
    public String userQuery(){
        return "userQuery";
    }

    @ResponseBody
    @RequestMapping("/userExport")
    @RequiresPermissions("user:export")
    public String userExport(){
        return "userExport";
    }
}