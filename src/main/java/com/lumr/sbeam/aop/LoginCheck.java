package com.lumr.sbeam.aop;

import com.lumr.sbeam.exception.LoginException;
import com.lumr.sbeam.vo.User;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * 登陆检查
 * Created by lumr on 2017/5/9.
 * @deprecated 已废弃，改用拦截器
 */
//@Aspect
@Deprecated
public class LoginCheck {
    @Autowired
    private HttpSession session;

    /**
     * 普通用户权限检测
     */
    @Before("execution(public String com.lumr.sbeam.controller.UserController.*(..)) " +
            "&& !execution(public String com.lumr.sbeam.controller.UserController.*Exception(..))")
    public void checkUser() {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new LoginException("你还没登陆");
    }

    /**
     * 管理员权限检测
     */
    @Before("execution(public String com.lumr.sbeam.controller.Admin*.*(..)) " +
            "&& !execution(public String com.lumr.sbeam.controller.Admin*.*Exception(..))")
    public void checkAdmin() {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getIsAdmin() != 1) {
            throw new LoginException("你不是管理员，没有修改权限。");
        }
    }
}
