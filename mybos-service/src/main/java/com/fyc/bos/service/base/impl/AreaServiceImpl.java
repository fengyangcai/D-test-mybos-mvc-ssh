package com.fyc.bos.service.base.impl;

import com.fyc.bos.dao.base.AreaDao;
import com.fyc.bos.entity.base.Area;
import com.fyc.bos.service.base.AreaService;
import com.fyc.bos.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: fyc
 * @Date: 2020/4/29 14:42
 */
@Service
@Transactional
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {

    private AreaDao areaDao;

    @Resource
    public void setAreaDao(AreaDao areaDao) {
        this.areaDao = areaDao;
        super.setBaseDao(areaDao);
    }
}
