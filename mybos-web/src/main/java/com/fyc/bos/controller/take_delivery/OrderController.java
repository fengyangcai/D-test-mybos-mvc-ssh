package com.fyc.bos.controller.take_delivery;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.take_delivery.Order;
import com.fyc.bos.service.BaseService;
import com.fyc.bos.service.take_delivery.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController<Order> {

    private OrderService orderService;

    @Resource
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
        super.setBaseService((BaseService<Order>) orderService);
    }

    /*
     * 根据订单号查询订单
     */
    @RequestMapping("/findByOderNum")
    public ResponseEntity<Order> findByOrderNum(Order order){
        Order persistOrder= orderService.findByOrderNum(order.getOrderNum());
        return ResponseEntity.ok(persistOrder);
    }

}
