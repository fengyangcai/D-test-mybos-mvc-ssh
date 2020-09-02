package com.fyc.bos.service.take_delivery.impl;

import com.fyc.bos.constant.Constants;
import com.fyc.bos.dao.base.AreaDao;
import com.fyc.bos.dao.base.FixedAreaDao;
import com.fyc.bos.dao.take_delivery.OrderDao;
import com.fyc.bos.dao.take_delivery.WorkBillDao;
import com.fyc.bos.entity.base.Area;
import com.fyc.bos.entity.base.Courier;
import com.fyc.bos.entity.base.FixedArea;
import com.fyc.bos.entity.base.SubArea;
import com.fyc.bos.entity.take_delivery.Order;
import com.fyc.bos.entity.take_delivery.WorkBill;
import com.fyc.bos.service.impl.BaseServiceImpl;
import com.fyc.bos.service.take_delivery.OrderService;
import com.fyc.bos.utils.SendSmsByaliUtils;
import com.fyc.crm.pojo.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
    //注入dao

    private OrderDao orderDao;

    @Resource
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        super.setBaseDao(orderDao);
    }

    //注入定区dao
    @Resource
    private FixedAreaDao fixedAreaDao;
    //注入区域dao
    @Resource
    private AreaDao areaDao;

    @Resource
    private WorkBillDao workBillDao;

    @Override
    public void saveOrder(Order order) {
        System.out.println("订单信息："+order);
        //生成订单号
        order.setOrderNum(UUID.randomUUID().toString());
        order.setStatus("1");
        order.setOrderTime(new Date());


        Area area = areaDao.findByProvinceAndCityAndDistrcit(
                order.getSendArea().getProvince(),
                order.getSendArea().getCity(),
                order.getSendArea().getDistrcit());
        //area:是持久态对象
        order.setSendArea(area);

        Area recArea = areaDao.findByProvinceAndCityAndDistrcit(
                order.getRecArea().getProvince(),
                order.getRecArea().getCity(),
                order.getRecArea().getDistrcit()
        );
        //area:是持久态对象
        order.setRecArea(recArea);

        //自动分单与人工分单
        //完成自动分单的逻辑
        /**
         * 方案一：根据下单的寄件人详细地址 匹配 客户地址 精确匹配 实现分单
         * 方案二：根据下单的寄件人的省市区以及详细地址 模糊 匹配 实现分单
         */
        //实现方案一：根据下单的寄件人详细地址 匹配 客户地址 精确匹配 实现分单
        //1.根据寄件人的详细地址查询客户表
        Customer customer = WebClient.create(Constants.CRM_URL + "/customerService/findByAddress?address=" + order.getSendAddress()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        //找到这个客户
        if (customer!=null){
            //获取该客户绑定的定区ID
            Integer fixedAreaId = customer.getFixedAreaId();
           if (fixedAreaId!=null){
               //找到定区
               FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
               if (fixedArea!=null){
                   Courier courier = fixedArea.getCourier();
                   if (courier!=null){
                       //通过定区找到快递员
                       System.out.println("根据下单的寄件人详细地址 匹配 客户地址 精确匹配 实现分单成功！由"+courier.getName()+"取件");

                       order.setOrderType("1");
                       orderDao.save(order);

                       //创建工作单
                       createWorkBill(order,courier);
                       return;
                   }
               }
           }

        }
        //方案二：根据下单的寄件人的省市区以及详细地址 模糊 匹配 实现分单
        //通过寄件人的省市区找到某个区域
        if(area!=null){
            //通过区域找到下面的所有分区
            Set<SubArea> subAreas = area.getSubAreas();
            //遍历所有分区
            for (SubArea subArea : subAreas) {
                //分区关键词和寄件人详细匹配
                if( order.getSendAddress().contains(subArea.getKeyWords())
                        || order.getSendAddress().contains(subArea.getAssitKeyWords())	){
                    //找到分区
                    FixedArea fixedArea = subArea.getFixedarea();
                    if(fixedArea!=null){
                        //通过分区找到定区
                        Courier courier = fixedArea.getCourier();
                        if(courier!=null){
                            //通过定区找到快递员
                            System.out.println("根据下单的寄件人的省市区以及详细地址 模糊 匹配 实现分单成功！由"+courier.getName()+"取件");

                            order.setOrderType("1");
                            orderDao.save(order);

                            //创建工作单
                            createWorkBill(order,courier);
                            return;
                        }
                    }
                }
            }
        }

        System.out.println("人工分单");
        order.setOrderType("2");
        orderDao.save(order);

    }

    /*
     * 根据订单号查询订单
     */
    @Override
    public Order findByOrderNum(String orderNum) {
        return orderDao.findByOrderNum(orderNum);
    }

    /**
     * 创建工作单，发送取件短信给快递员
     */
    public void createWorkBill(Order order,Courier courier){
        WorkBill wb = new WorkBill();
        wb.setBuildtime(new Date());//创建时间
        wb.setOrder(order);//所属订单
        wb.setPickstate("1");
        wb.setCourier(courier);//关联快递员
        wb.setRemark("指派给由"+courier.getName()+"取件");

        workBillDao.save(wb);

        //发送短信给快递员
        try {
            SendSmsByaliUtils.sendSms(
                    courier.getTelephone(),
                    "德馨人力",
                    "SMS_176531295",
                    "{\"name\":\""+courier.getName()+"\",\"phone\":\""+order.getSendMobile()+"\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
