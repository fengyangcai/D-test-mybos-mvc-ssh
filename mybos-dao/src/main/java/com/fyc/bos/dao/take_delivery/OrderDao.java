package com.fyc.bos.dao.take_delivery;

import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.entity.take_delivery.Order;

public interface OrderDao extends BaseDao<Order> {


   public Order findByOrderNum(String orderNum);
}
