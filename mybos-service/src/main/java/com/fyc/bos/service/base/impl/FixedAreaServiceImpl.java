package com.fyc.bos.service.base.impl;

import com.fyc.bos.dao.base.FixedAreaDao;
import com.fyc.bos.entity.base.FixedArea;
import com.fyc.bos.service.BaseService;
import com.fyc.bos.service.base.FixedAreaService;
import com.fyc.bos.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: fyc
 * @Date: 2020/4/30 17:11
 */

@Service
@Transactional
public class FixedAreaServiceImpl extends BaseServiceImpl<FixedArea> implements FixedAreaService {

    private FixedAreaDao fixedAreaDao;

    @Resource
    public void setFixedAreaDao(FixedAreaDao fixedAreaDao) {
        this.fixedAreaDao = fixedAreaDao;
        super.setBaseDao(fixedAreaDao);
    }
}
