package com.fyc.bos.service.take_delivery.impl;

import com.fyc.bos.dao.take_delivery.WayBillDao;
import com.fyc.bos.entity.take_delivery.WayBill;
import com.fyc.bos.service.impl.BaseServiceImpl;
import com.fyc.bos.service.take_delivery.WayBillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class WayBillServiceImpl extends BaseServiceImpl<WayBill> implements WayBillService {
    //注入dao
    private WayBillDao wayBillDao;

    @Resource
    public void setWayBillDao(WayBillDao wayBillDao) {
        this.wayBillDao = wayBillDao;
        //把WayBillDao赋值给baseDao
        super.setBaseDao(wayBillDao);
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillDao.findByWayBillNum( wayBillNum);
    }
}
