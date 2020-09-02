package com.fyc.bos.dao.take_delivery;

import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.entity.take_delivery.WayBill;

public interface WayBillDao extends BaseDao<WayBill> {
    WayBill findByWayBillNum(String wayBillNum);
}
