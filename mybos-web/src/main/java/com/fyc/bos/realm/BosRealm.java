package com.fyc.bos.realm;

import com.fyc.bos.dao.system.UserDao;
import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.entity.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;


import java.util.List;


public class BosRealm extends AuthorizingRealm {

  @javax.annotation.Resource
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权操作");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User loginUser= (User) subject.getPrincipal();

        //获取当前登录用户的所有授权码
        List<String> permsList = userDao.findMyperms(loginUser.getId());
        for (String perms: permsList) {
            if (perms!=null&&!perms.trim().equals("")){
                info.addStringPermission(perms);
            }
        }
        return info;

    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证操作");
        //取出用户输入的数据
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userDao.findByUsername(token.getUsername());

        if (user == null) {
            return null;
        }


        //返回数据库的密码给shiro
        /**
         * pricipals：登录成功后，需要返回给login方法的信息
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(), user.getUsername());
    }
}
