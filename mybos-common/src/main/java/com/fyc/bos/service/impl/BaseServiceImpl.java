package com.fyc.bos.service.impl;


import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Author: fyc
 * @Date: 2020/4/26 10:40
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    //注入dao
    //@Resource //到spring环境中找一个BaseDao类型的对象，找到赋值给baseDao变量
    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List findAll() {

        return baseDao.findAll();
    }

    @Override
    public Page findAll(Specification spec, Pageable pageable) {

        return baseDao.findAll(spec, pageable);
    }

    @Override
    public void save(T model) {
        baseDao.save(model);
    }

    @Override
    public T findById(Integer uuid) {

        return baseDao.findOne(uuid);
    }

    @Override
    public void delete(String ids) {
        if (ids != null && !ids.trim().equals("")) {
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {

                baseDao.delete(Integer.parseInt(idArr[i]));
            }
        }
    }

    @Override
    public void save(List<T> list) {
        baseDao.save(list);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return baseDao.findAll(spec);
    }
}
