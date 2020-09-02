package com.fyc.bos.service.system.impl;

import com.fyc.bos.dao.system.ResourceDao;
import com.fyc.bos.dao.system.RoleDao;
import com.fyc.bos.dao.system.UserDao;
import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.entity.system.Role;
import com.fyc.bos.entity.system.User;
import com.fyc.bos.service.impl.BaseServiceImpl;
import com.fyc.bos.service.system.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Transactional//事务做在业务层
public class RoleServiceImpl extends BaseServiceImpl<Role>  implements RoleService {

    private RoleDao roleDao;
    @javax.annotation.Resource
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
        super.setBaseDao(roleDao);
    }

    @javax.annotation.Resource
    private ResourceDao resourceDao;

    @javax.annotation.Resource
    private UserDao userDao;

    @Override
    public void save(Role model) {
        Integer roleId = model.getId();
        if (roleId!=null){
            Role role = roleDao.findOne(roleId);
            model.setResources(role.getResources());
            super.save(model);
        }else {
            super.save(model);
        }
    }

    @Override
    public void bindResToRole(Integer roleId, String resIds) {
        //1.清除该角色绑定过的资源
        Role persistRole = roleDao.findOne(roleId);// persistRole: 持久态（任何的更新操作 操作数据库）
        persistRole.setResources(null);
        //2.把新的资源绑定到该角色上
        HashSet<Resource> resourceHashSet = new HashSet<>();

        if (resIds!=null&&!resIds.trim().equals("")){
            String[] resIdArray = resIds.split(",");
            for (int i = 0; i < resIdArray.length; i++) {
                resourceHashSet.add(resourceDao.findOne(new Integer(resIdArray[i])));

            }

        }
        persistRole.setResources(resourceHashSet);
        System.out.println("角色保存资源begin");
        roleDao.save(persistRole);

    }

    @Override
    public Page<Role> findAll(Specification<Role> spec, PageRequest pageRequest, Integer userId) {
        User user = userDao.findOne(userId);
        Set<Role> roles = user.getRoles();
        Page<Role> rolePage = roleDao.findAll(spec, pageRequest);
        CopyOnWriteArraySet<Integer> arraySet = new CopyOnWriteArraySet<>();

        for (Role role : roles) {
            arraySet.add(role.getId());
        }
        for (Role role : rolePage.getContent()) {
            role.setChecked(arraySet.contains(role.getId()));
        }
        return rolePage;
    }
}
