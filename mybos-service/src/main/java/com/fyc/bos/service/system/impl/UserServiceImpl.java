package com.fyc.bos.service.system.impl;

import com.fyc.bos.dao.system.RoleDao;
import com.fyc.bos.dao.system.UserDao;
import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.entity.system.Role;
import com.fyc.bos.entity.system.User;
import com.fyc.bos.exception.IncorrectPasswordException;
import com.fyc.bos.exception.NoAccountException;
import com.fyc.bos.service.impl.BaseServiceImpl;
import com.fyc.bos.service.system.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User>implements UserService {
    //注入dao
    private UserDao userDao;

    @javax.annotation.Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        super.setBaseDao(userDao);
    }
   @javax.annotation.Resource
    private RoleDao roleDao;

   /* public static void main(String[] args) {
        User model=new User();
        model.setPassword("123");
        model.setUsername("root");
        Md5Hash md5Hash = new Md5Hash(model.getPassword(), model.getUsername(), 2);
        System.out.println(md5Hash.toString());
    }*/

    @Override
    public void save(User model) {

        //对密码进行md5加密
        Md5Hash md5Hash = new Md5Hash(model.getPassword(), model.getUsername(), 2);
        model.setPassword(md5Hash.toString());

        // 判断修改操作
        if (model.getId()!=null){
            User user = userDao.findOne(model.getId());
            model.setRoles(user.getRoles());
            super.save(model);
        }else {
            super.save(model);
        }
    }

    @Override
    public void bindRoleToUser(Integer userId, String roleIds) {
        User user = userDao.findOne(userId);

        HashSet<Role> roleIdHashSet = new HashSet<>();
        if (roleIds!=null&&!roleIds.trim().equals("")){
            String[] split = roleIds.split(",");
            for (String roleIdStr : split) {
                Role role = roleDao.findOne( new Integer(roleIdStr));
                roleIdHashSet.add(role);
            }
        }
        //把之前的清空
        user.setRoles(null);
        //重写赋值
        System.out.println("用户进行保存角色的-------------");
        user.setRoles(roleIdHashSet);
        userDao.save(user);
    }

    @Override
    public User login(User user) throws NoAccountException, IncorrectPasswordException {
        User loginUser = userDao.findByUsername(user.getUsername());
        if (loginUser==null){
            //用户不存在
            throw new NoAccountException("用户名不存在");
        }
        //判断密码是否正确
        if (!loginUser.getPassword().equals(user.getPassword())){
            throw new IncorrectPasswordException("密码不正确");
        }
        return loginUser;

    }

    @Override
    public List<Resource> findMyMenus(Integer id) {
        List<Resource> resourceList = userDao.findMyMenus(id);

        //取出重复资源
        removeDuplicateWithOrder(resourceList);

        return resourceList;
    }

    /**
     * 排除List集合中的重复元素
     * @param list
     */

    public static void removeDuplicateWithOrder(List list) {
        //创建Set集合，用于排除重复数据的
        HashSet<Object> set = new HashSet<>();
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
             //取出每个元素
            Object element = iter.next();
            //如果元素不重复，则添加到List集合
            if (set.add(element))
                arrayList.add(element);


        }
        list.clear();
        list.addAll(arrayList);


    }
}
