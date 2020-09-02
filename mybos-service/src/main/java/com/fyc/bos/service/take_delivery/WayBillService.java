package com.fyc.bos.service.take_delivery;

import com.fyc.bos.entity.take_delivery.WayBill;
import com.fyc.bos.service.BaseService;

public interface WayBillService extends BaseService<WayBill> {
    WayBill findByWayBillNum(String wayBillNum);
}
