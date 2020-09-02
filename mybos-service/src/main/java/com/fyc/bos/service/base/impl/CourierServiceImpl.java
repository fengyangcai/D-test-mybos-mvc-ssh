package com.fyc.bos.service.base.impl;

import com.fyc.bos.dao.base.CourierDao;
import com.fyc.bos.entity.base.Courier;
import com.fyc.bos.service.base.CourierService;
import com.fyc.bos.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: fyc
 * @Date: 2020/4/27 15:01
 */
@Service
@Transactional
public class CourierServiceImpl extends BaseServiceImpl<Courier> implements CourierService {

    private CourierDao courierDao;

    @Resource
    public void setCourierDao(CourierDao courierDao) {
        this.courierDao = courierDao;
        super.setBaseDao(courierDao);
    }
}
