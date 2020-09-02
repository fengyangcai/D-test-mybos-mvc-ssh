package com.fyc.bos.service.base.impl;

import com.fyc.bos.dao.base.SubAreaDao;
import com.fyc.bos.entity.base.SubArea;
import com.fyc.bos.service.base.SubAreaService;
import com.fyc.bos.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: fyc
 * @Date: 2020/4/30 23:43
 */
@Service
@Transactional
public class SubAreaServiceImpl extends BaseServiceImpl<SubArea> implements SubAreaService {

    private SubAreaDao subAreaDao;
    @Resource
    public void setSubAreaDao(SubAreaDao subAreaDao) {
        this.subAreaDao = subAreaDao;
        super.setBaseDao(subAreaDao);
    }
}
