package com.fyc.bos.service.system.impl;

import com.fyc.bos.dao.system.ResourceDao;
import com.fyc.bos.dao.system.RoleDao;
import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.entity.system.Role;
import com.fyc.bos.service.impl.BaseServiceImpl;
import com.fyc.bos.service.system.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

    private ResourceDao resourceDao;

    @javax.annotation.Resource
    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
        super.setBaseDao(resourceDao);
    }

    @javax.annotation.Resource
    private RoleDao roleDao;

    //查询绑定角色的资源
    @Override
    public Page<Resource> findAll(Specification<Resource> spc, PageRequest pageRequest, Integer roleId) {
        Page<Resource> page = resourceDao.findAll(spc, pageRequest);
        //判断角色绑定过的资源，然后这些资源它们的checked属性改为true
        Role role = roleDao.findOne(roleId);
        Set<Resource> resources = role.getResources();
        //把绑定过的资源ID封装起来
        HashSet<Integer> resIdSet = new HashSet<>();
        for (Resource resource : resources) {
            resIdSet.add(resource.getId());//如 1,10,23
        }
        for (Resource res : page.getContent()) {//40个id ，包含如 1,10,23
            res.setChecked(resIdSet.contains(res.getId()));
        }
        return page;

    }
}
