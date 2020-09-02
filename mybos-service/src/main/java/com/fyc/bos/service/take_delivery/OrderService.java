package com.fyc.bos.service.take_delivery;

import com.fyc.bos.entity.take_delivery.Order;
import com.fyc.bos.service.BaseService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface OrderService extends BaseService<Order> {
    /**
     * 保存订单
     */
    @POST
    @Path("/saveOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOrder(Order order);


    Order findByOrderNum(String orderNum);
}

