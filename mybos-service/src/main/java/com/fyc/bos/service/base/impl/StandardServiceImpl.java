package com.fyc.bos.service.base.impl;

import com.fyc.bos.dao.base.StandardDao;
import com.fyc.bos.service.base.StandardService;
import com.fyc.bos.entity.base.Standard;
import com.fyc.bos.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: fyc
 * @Date: 2020/4/25 6:36
 */
@Service
@Transactional //事物做在业务层
public class StandardServiceImpl extends BaseServiceImpl<Standard> implements StandardService {


    private StandardDao standardDao;

    @Resource
    public void setStandardDao(StandardDao standardDao) {
        this.standardDao = standardDao;
        //把StandardDao赋值给baseDao
        super.setBaseDao(standardDao);
    }
}
